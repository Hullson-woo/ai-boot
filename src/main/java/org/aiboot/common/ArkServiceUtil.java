package org.aiboot.common;

import com.alibaba.fastjson.JSONObject;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.aiboot.common.entity.ArkMessageBody;
import org.aiboot.constant.ChatConstant;
import org.aiboot.utils.SseEmitterUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * <p>火山引擎服务实例工具</p>
 *
 * @author Hullson
 * @date 2025-06-30
 */
@Component
@Slf4j
public class ArkServiceUtil {

    @Value("${ai.deepseek.api-key}")
    private String apiKey;
    private static final Map<String, Disposable> sessionDisposables = new ConcurrentHashMap<>();

    /**
     * 构建火山引擎服务实例
     * @return  火山引擎服务实例
     */
    public ArkService build() {
        ArkService service = ArkService.builder()
                .apiKey(apiKey)
                .build();
        return service;
    }

    /**
     * 构建对话消息体列表
     * @param contents  用户对话内容列表
     * @return          对话消息体列表
     */
    public List<ChatMessage> buildChatMessage(List<String> contents) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (String content : contents) {
            ChatMessage userMessage = ChatMessage.builder()
                    .role(ChatMessageRole.USER)
                    .content(content)
                    .build();
            chatMessages.add(userMessage);
        }

        return chatMessages;
    }

    /**
     * <p>通过火山引擎服务接口进行对话-流式输出</p>
     * @param body              消息体
     * @param callback          回调者
     */
    public SseEmitter send(ArkMessageBody body,  Consumer<JSONObject> callback) throws IOException {
        String chatSessionId = body.getChatSessionId();
        String model = body.getModel();
        List<ChatMessage> chatMessages = body.getChatMessages();

        // 创建SSE Emitter
        SseEmitter emitter = SseEmitterUtil.create(chatSessionId);

        // 创建火山引擎服务实例
        ArkService service = build();

        // 构建对话请求体
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(model)
                .messages(chatMessages)
                .build();

        sendOnReady(emitter, body.getRequestMessageId(), body.getResponseMessageId());

        // 调用AI流式输出接口
        StringJoiner reasoningJoiner = new StringJoiner("");
        StringJoiner messageJoiner = new StringJoiner("");
        Disposable disposable = service.streamChatCompletion(chatCompletionRequest)
                .doOnCancel(() -> {
                    log.info("---- 用户终止对话\t{} ----", chatSessionId);
                    sendOnComplete(emitter, reasoningJoiner, messageJoiner, callback, false);
                    emitter.complete();
                })
                .doOnComplete(() -> {
                    log.info("---- 对话输出已完成\t{} ----", chatSessionId);
                    sendOnComplete(emitter, reasoningJoiner, messageJoiner, callback, true);
                    emitter.complete();
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe(choice -> {
                    if (choice.getChoices().size() > 0) {
                        ChatMessage replyMessage = choice.getChoices().get(0).getMessage();
                        if (StringUtils.isNotBlank(replyMessage.getReasoningContent())) {
                            emitter.send(SseEmitter.event()
                                    .name(ChatConstant.MESSAGE_TYPE_REASONING_MESSAGE)
                                    .data(replyMessage.getReasoningContent(), MediaType.TEXT_PLAIN));
                            reasoningJoiner.add(replyMessage.getReasoningContent());
                        } else {
                            emitter.send(SseEmitter.event()
                                    .name(ChatConstant.MESSAGE_TYPE_MESSAGE)
                                    .data(replyMessage.getContent(), MediaType.TEXT_PLAIN));
                            Object content = replyMessage.getContent();
                            if (content instanceof String) {
                                messageJoiner.add((String) content);
                            } else {
                                messageJoiner.add(JSONObject.toJSONString(replyMessage.getContent()));
                            }

                        }
                    }
                });
        sessionDisposables.put(chatSessionId, disposable);

        // 结束火山引擎服务
        service.shutdownExecutor();
        return emitter;
    }

    public void stop(String chatSessionId) {
        Disposable disposable = sessionDisposables.get(chatSessionId);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            sessionDisposables.remove(chatSessionId);
        }
    }

    /**
     * 发送就绪态消息
     * @param emitter               SSE Emitter
     * @param requestMessageId      请求消息ID
     * @param responseMessageId     响应消息ID
     * @throws IOException          IOException
     */
    private void sendOnReady(SseEmitter emitter, Integer requestMessageId, Integer responseMessageId) throws IOException {
        // 发送对话就绪初始消息
        JSONObject replyStartInfo = new JSONObject();
        replyStartInfo.put("request_message_id", requestMessageId);
        replyStartInfo.put("response_message_id", responseMessageId);
        emitter.send(SseEmitter.event()
                .name(ChatConstant.MESSAGE_TYPE_READY)
                .data(JSONObject.toJSONString(replyStartInfo)));

        // 发送对话窗口更新时间
        JSONObject updateSessionInfo = new JSONObject();
        updateSessionInfo.put("updated_at", new Date().getTime());
        emitter.send(SseEmitter.event()
                .name(ChatConstant.MESSAGE_TYPE_UPDATE_SESSION)
                .data(JSONObject.toJSONString(updateSessionInfo)));
    }

    /**
     * 发送结束态消息
     * @param emitter           SSE Emitter
     * @param reasoningJoiner   深度思考拼接内容
     * @param messageJoiner     普通消息拼接内容
     * @param callback          回调
     * @param isComplete        是否正常完成
     * @throws IOException
     */
    private void sendOnComplete(SseEmitter emitter,
                                StringJoiner reasoningJoiner,
                                StringJoiner messageJoiner,
                                Consumer<JSONObject> callback,
                                Boolean isComplete) throws IOException {
        // 发送对话结束信号
        emitter.send(SseEmitter.event()
                .name(ChatConstant.MESSAGE_TYPE_FINISH));

        // 发送对话窗口更新时间
        JSONObject updateSessionInfo = new JSONObject();
        updateSessionInfo.put("updated_at", new Date().getTime());
        emitter.send(SseEmitter.event()
                .name(ChatConstant.MESSAGE_TYPE_UPDATE_SESSION)
                .data(JSONObject.toJSONString(updateSessionInfo)));

        // 发送对话关闭行为状态  terminate-用户终止  finish-自然结束
        emitter.send(SseEmitter.event()
                .name(ChatConstant.MESSAGE_TYPE_END_STATUS)
                .data(isComplete ? ChatConstant.CHAT_END_STATUS_FINISH : ChatConstant.CHAT_END_STATUS_TERMINATE));

        JSONObject result = new JSONObject();
        result.put("reasoning", reasoningJoiner);
        result.put("message", messageJoiner);
        result.put("status", isComplete ? ChatConstant.CHAT_END_STATUS_FINISH : ChatConstant.CHAT_END_STATUS_TERMINATE);

        callback.accept(result);
    }


}
