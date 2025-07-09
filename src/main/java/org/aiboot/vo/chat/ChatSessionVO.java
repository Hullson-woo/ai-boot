package org.aiboot.vo.chat;

import lombok.Data;

/**
 * <p>AI工具会话</p>
 *
 * @author Hullson
 * @date 2025-06-01
 * @since 1.0
 */
@Data
public class ChatSessionVO {
    private Long id;
    private String chatSessionId;
    private String userId;
    private String title;
}
