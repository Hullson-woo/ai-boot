package org.aiboot.mapper.chat;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.aiboot.entity.chat.ChatSession;
import org.aiboot.vo.chat.ChatSessionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatSessionMapper extends BaseMapper<ChatSession> {
    void delete(@Param("chatSessionId") String chatSessionId);
    ChatSessionVO get(@Param("chatSessionId") String chatSessionId);
    List<ChatSessionVO> listHistory(@Param("userId") String userId);
}
