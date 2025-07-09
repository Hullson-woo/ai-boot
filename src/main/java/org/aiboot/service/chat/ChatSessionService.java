package org.aiboot.service.chat;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aiboot.entity.chat.ChatSession;
import org.aiboot.vo.chat.ChatSessionVO;

import java.util.List;

public interface ChatSessionService extends IService<ChatSession> {
    String create();
    void delete(String chatSessionId);
    void rename(String chatSessionId, String title);

    ChatSessionVO get(String chatSessionId);
    List<ChatSessionVO> listHistory();
}
