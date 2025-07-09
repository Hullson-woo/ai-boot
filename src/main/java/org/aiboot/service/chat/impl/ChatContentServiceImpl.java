package org.aiboot.service.chat.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.aiboot.common.AiModelUtil;
import org.aiboot.common.ArkServiceUtil;
import org.aiboot.common.entity.ArkMessageBody;
import org.aiboot.common.exception.ServiceException;
import org.aiboot.constant.ChatConstant;
import org.aiboot.dto.chat.ChatContentDTO;
import org.aiboot.dto.chat.ChatRegenerateDTO;
import org.aiboot.entity.chat.ChatContent;
import org.aiboot.mapper.chat.ChatContentMapper;
import org.aiboot.service.chat.ChatContentService;
import org.aiboot.vo.chat.ChatContentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * <p>AI工具会话</p>
 *
 * @author Hullson
 * @date 2025-06-04
 * @since 1.0
 */
@Service
@Slf4j
public class ChatContentServiceImpl extends ServiceImpl<ChatContentMapper, ChatContent> implements ChatContentService {
    @Autowired
    private ArkServiceUtil arkServiceUtil;
    @Autowired
    private AiModelUtil aiModelUtil;

    /**
     * 发送AI对话
     * @param chatContentDTO    对话内容DTO
     * @return                  SSE Emitter
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, IOException.class})
    public SseEmitter communication(ChatContentDTO chatContentDTO) {
        Integer requestMessageId = Optional.ofNullable(chatContentDTO.getParentMessageId())
                .map(parentMessageId -> parentMessageId + 1)
                .orElse(1);
        chatContentDTO.setMessageId(requestMessageId);
        ChatContent chatContent = save(chatContentDTO);

        Integer responseMessageId = requestMessageId + 1;

        // 构建AI对话消息
        List<ChatMessage> chatMessages = arkServiceUtil.buildChatMessage(Collections.singletonList(chatContentDTO.getContent()));

        // 匹配调用模型
        String executorModel = aiModelUtil.matchAiModel(chatContentDTO.getModel(), chatContentDTO.getThinkingEnabled());

        // 构建消息体
        ArkMessageBody messageBody = new ArkMessageBody();
        messageBody.setChatSessionId(chatContentDTO.getChatSessionId())
                .setModel(executorModel)
                .setRequestMessageId(requestMessageId)
                .setResponseMessageId(responseMessageId)
                .setThinkingEnabled(chatContentDTO.getThinkingEnabled())
                .setSearchEnabled(chatContentDTO.getSearchEnabled())
                .setChatMessages(chatMessages);

        SseEmitter emitter;
        try {
            // 发送AI消息
            emitter = send(messageBody);
        } catch (IOException e) {
            throw new ServiceException("服务器繁忙，请重试");
        }

        return emitter;
    }

    /**
     * 用户侧中断对话
     * @param chatSessionId 对话窗口ID
     * @return              SSE Emitter
     */
    @Override
    public void stopStream(String chatSessionId) {
        arkServiceUtil.stop(chatSessionId);
    }

    /**
     * <p>重新生成对话</p>
     * <ui>重新生成逻辑：
     * <li>根据childMessageId获取需要重新生成对话的上文内容</li>
     * <li>新生成对话parent_message_id与旧对话相同</li>
     * </ui>
     * @param regenerateDTO
     * @return
     */
    @Override
    public SseEmitter regenerate(ChatRegenerateDTO regenerateDTO) {
        Integer responseMessageId = regenerateDTO.getCurrentMessageId() + 1;
        ChatContentVO chatContentVO = getByChatSessionIdAndMessageId(regenerateDTO.getChatSessionId(), regenerateDTO.getCurrentMessageId());
        ChatContentVO requestChatContentVo = getByChatSessionIdAndMessageId(chatContentVO.getChatSessionId(), chatContentVO.getParentMessageId());

        // 构建AI对话消息
        List<ChatMessage> chatMessages = arkServiceUtil.buildChatMessage(
                Collections.singletonList(requestChatContentVo.getContent()));

        // 匹配调用模型
        String executorModel = chatContentVO.getModel();

        // 构建消息体
        ArkMessageBody messageBody = new ArkMessageBody();
        messageBody.setChatSessionId(regenerateDTO.getChatSessionId())
                .setModel(executorModel)
                .setRequestMessageId(chatContentVO.getParentMessageId())
                .setResponseMessageId(responseMessageId)
                .setThinkingEnabled(regenerateDTO.getThinkingEnabled())
                .setSearchEnabled(regenerateDTO.getSearchEnabled())
                .setChatMessages(chatMessages);

        SseEmitter emitter;
        try {
            // 发送AI消息
            emitter = send(messageBody);
        } catch (IOException e) {
            throw new ServiceException("服务器繁忙，请重试");
        }

        return emitter;
    }

    /**
     * 根据会话窗口ID和消息ID获取对话记录
     * @param chatSessionId     会话窗口ID
     * @param messageId         消息ID
     * @return                  对话记录
     */
    @Override
    public ChatContentVO getByChatSessionIdAndMessageId(String chatSessionId, Integer messageId) {
        return baseMapper.getByChatSessionIdAndMessageId(chatSessionId, messageId);
    }

    /**
     * 获取会话窗口历史对话记录
     * @param chatSessionId 会话窗口ID
     * @return              历史对话记录
     */
    @Override
    public List<ChatContentVO> list(String chatSessionId) {
        return baseMapper.list(chatSessionId);
    }

    /**
     * 保存用户对话
     * @param chatContentDTO    用户对话消息内容
     * @return                  对话消息内容
     */
    private ChatContent save(ChatContentDTO chatContentDTO) {
        ChatContent chatContent = new ChatContent();
        chatContent.setChatSessionId(chatContentDTO.getChatSessionId())
                .setParentMessageId(chatContentDTO.getParentMessageId())
                .setMessageId(chatContentDTO.getMessageId())
                .setMessageType(ChatConstant.MESSAGE_REQUEST)
                .setMessageRole(ChatConstant.MESSAGE_ROLE_USER)
                .setModel(chatContentDTO.getModel())
                .setContent(chatContentDTO.getContent())
                .setThinkingEnabled(chatContentDTO.getThinkingEnabled())
                .setSearchEnabled(chatContentDTO.getSearchEnabled())
                .setDocUrl(chatContentDTO.getDocUrl())
                .setImageUrl(chatContentDTO.getImageUrl());
        chatContent.preInsert();

        baseMapper.insert(chatContent);

        return chatContent;
    }

    /**
     * 发送对话内容
     * @param messageBody       对话消息体
     * @return                  SSE Emitter
     * @throws IOException      IOException
     */
    private SseEmitter send(ArkMessageBody messageBody) throws IOException {
        // 发送消息
        SseEmitter emitter = arkServiceUtil.send(messageBody,
                result -> {
                    String messageSignal = ChatConstant.MESSAGE_SIGNAL_NORMAL;
                    StringJoiner reasoningJoiner = result.getObject("reasoning", StringJoiner.class);
                    StringJoiner messageJoiner = result.getObject("message", StringJoiner.class);
                    String status = result.getString("status");

                    if (ChatConstant.CHAT_END_STATUS_TERMINATE.equals(status)) {
                        messageSignal = ChatConstant.MESSAGE_SIGNAL_TERMINATE;
                    }

                    ChatContent replyChatContent = new ChatContent();
                    replyChatContent.setChatSessionId(messageBody.getChatSessionId())
                            .setParentMessageId(messageBody.getRequestMessageId())
                            .setMessageId(messageBody.getResponseMessageId())
                            .setMessageType(ChatConstant.MESSAGE_RESPONSE)
                            .setMessageSignal(messageSignal)
                            .setMessageRole(ChatConstant.MESSAGE_ROLE_ASSISTANT)
                            .setModel(messageBody.getModel())
                            .setThinkingContent(reasoningJoiner.toString())
                            .setContent(messageJoiner.toString())
                            .setThinkingEnabled(messageBody.getThinkingEnabled())
                            .setSearchEnabled(messageBody.getSearchEnabled());
                    replyChatContent.preInsert();

                    baseMapper.insert(replyChatContent);
                });

        // 保存当前对话
        return emitter;
    }
}
