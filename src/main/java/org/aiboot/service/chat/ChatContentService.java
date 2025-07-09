package org.aiboot.service.chat;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aiboot.dto.chat.ChatContentDTO;
import org.aiboot.dto.chat.ChatRegenerateDTO;
import org.aiboot.entity.chat.ChatContent;
import org.aiboot.vo.chat.ChatContentVO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;


public interface ChatContentService extends IService<ChatContent> {
    SseEmitter communication(ChatContentDTO chatContentDTO) throws IOException;
    void stopStream(String chatSessionId);
    SseEmitter regenerate(ChatRegenerateDTO regenerateDTO);

    ChatContentVO getByChatSessionIdAndMessageId(String chatSessionId, Integer messageId);
    List<ChatContentVO> list(String chatSessionId);
}
