package org.aiboot.dto.chat;

import lombok.Data;

/**
 * <p>AI工具请求会话</p>
 *
 * @author Hullson
 * @date 2025-06-01
 * @since 1.0
 */
@Data
public class ChatContentDTO {
    private String chatSessionId;
    private Integer messageId;
    private Integer parentMessageId;
    private String model;
    private String content;
    private Boolean thinkingEnabled;
    private Boolean searchEnabled;
    private String docUrl;
    private String imageUrl;

}
