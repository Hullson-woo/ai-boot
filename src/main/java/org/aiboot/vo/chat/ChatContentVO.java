package org.aiboot.vo.chat;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>AI工具响应会话</p>
 *
 * @author Hullson
 * @date 2025-06-04
 * @since 1.0
 */
@Data
public class ChatContentVO {
    private String chatSessionId;
    private Integer messageId;
    private Integer parentMessageId;
    private String messageType;
    private String messageSignal;
    private String messageRole;
    private String model;
    private String systemSetup;
    private String content;
    private String thinkingContent;
    private Boolean thinkingEnabled;
    private Boolean searchEnabled;
    private Integer tokenUsage;
    private BigDecimal tokenCost;
    private String docUrl;
    private String imageUrl;
}
