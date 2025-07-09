package org.aiboot.service.chat.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aiboot.common.exception.ServiceException;
import org.aiboot.constant.SystemConstant;
import org.aiboot.mapper.chat.ChatSessionMapper;
import org.aiboot.entity.chat.ChatSession;
import org.aiboot.service.chat.ChatSessionService;
import org.aiboot.vo.chat.ChatSessionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>AI工具会话</p>
 *
 * @author Hullson
 * @date 2025-06-01
 * @since 1.0
 */
@Service
@Slf4j
public class ChatSessionServiceImpl
        extends ServiceImpl<ChatSessionMapper, ChatSession>
        implements ChatSessionService {

    /**
     * 创建会话
     * @return  会话ID
     */
    @Override
    public String create() {
        String userId = "0b48216f156d46219c1c11b3a7de41c3";
        ChatSession chatSession = new ChatSession();
        String chatSessionId = UUID.randomUUID().toString();
        chatSession.setChatSessionId(chatSessionId)
                .setUserId(userId)
                .setTitle("新会话");
        chatSession.preInsert();

        try {
            baseMapper.insert(chatSession);
            log.info("---- ChatSessionServiceImpl#create 创建会话 {} ----", chatSessionId);
            return chatSessionId;
        } catch (Exception e) {
            log.error("---- ChatSessionServiceImpl#create 创建会话失败 ----");
            e.printStackTrace();
            throw new ServiceException("创建会话失败");
        }
    }

    /**
     * 删除会话记录
     * @param chatSessionId 会话ID
     * @return              删除状态
     */
    @Override
    public void delete(String chatSessionId) {
        LambdaQueryWrapper<ChatSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatSession::getDelFlag, SystemConstant.DEL_FLAG_NORMAL)
                .eq(ChatSession::getChatSessionId, chatSessionId);

        ChatSession chatSession = baseMapper.selectOne(wrapper);
        chatSession.setDelFlag(SystemConstant.DEL_FLAG_DELETE);
        chatSession.preUpdate();

        baseMapper.updateById(chatSession);
    }

    /**
     * 重命名会话名称
     * @param chatSessionId         会话ID
     * @param title       会话名称
     */
    @Override
    public void rename(String chatSessionId, String title) {
        LambdaQueryWrapper<ChatSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatSession::getDelFlag, SystemConstant.DEL_FLAG_NORMAL)
                .eq(ChatSession::getChatSessionId, chatSessionId);
        ChatSession chatSession = baseMapper.selectOne(wrapper);
        chatSession.setTitle(title);
        chatSession.preUpdate();
        baseMapper.updateById(chatSession);
    }

    /**
     * 根据会话ID获取会话记录
     * @param chatSessionId     会话ID
     * @return                  会话
     */
    @Override
    public ChatSessionVO get(String chatSessionId) {
        LambdaQueryWrapper<ChatSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatSession::getDelFlag, SystemConstant.DEL_FLAG_NORMAL)
                .eq(ChatSession::getChatSessionId, chatSessionId);
        ChatSession chatSession = baseMapper.selectOne(wrapper);

        ChatSessionVO chatSessionVO = new ChatSessionVO();
        BeanUtils.copyProperties(chatSession, chatSessionVO);
        return chatSessionVO;
    }

    /**
     * 获取登录用户最近会话
     * @return  会话数据列表
     */
    @Override
    public List<ChatSessionVO> listHistory() {
        String userId = "0b48216f156d46219c1c11b3a7de41c3";

        LambdaQueryWrapper<ChatSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ChatSession::getDelFlag, SystemConstant.DEL_FLAG_NORMAL)
                .eq(ChatSession::getUserId, userId)
                .orderByDesc(ChatSession::getCreateDate);
        List<ChatSession> chatSessions = baseMapper.selectList(wrapper);

        List<ChatSessionVO> chatSessionVOS = chatSessions.stream()
                .map(entity -> {
                    ChatSessionVO chatSessionVO = new ChatSessionVO();
                    BeanUtils.copyProperties(entity, chatSessionVO);
                    return chatSessionVO;
                }).collect(Collectors.toList());
        return chatSessionVOS;
    }
}
