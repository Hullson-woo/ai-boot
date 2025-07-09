package org.aiboot.mapper.chat;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.aiboot.entity.chat.ChatContent;
import org.aiboot.vo.chat.ChatContentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChatContentMapper extends BaseMapper<ChatContent> {
    ChatContentVO getByChatSessionIdAndMessageId(@Param("chatSessionId") String chatSessionId, @Param("messageId") Integer messageId);
    List<ChatContentVO> list(@Param("chatSessionId") String chatSessionId);
}
