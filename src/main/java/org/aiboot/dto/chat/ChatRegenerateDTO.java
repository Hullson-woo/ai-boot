package org.aiboot.dto.chat;

import lombok.Data;

/**
 * <p>AI对话重新生成</p>
 *
 * @author Hullson
 * @date 2025-07-01
 */
@Data
public class ChatRegenerateDTO {
    private String chatSessionId;
    private Integer currentMessageId;
    private Boolean thinkingEnabled;
    private Boolean searchEnabled;
}
