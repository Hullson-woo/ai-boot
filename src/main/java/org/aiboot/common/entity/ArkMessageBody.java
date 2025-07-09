package org.aiboot.common.entity;

import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>火山引擎服务接口消息体</p>
 *
 * @author Hullson
 * @date 2025-06-30
 */
@Data
@Accessors(chain = true)
public class ArkMessageBody {
    private String chatSessionId;
    private String model;
    private Integer requestMessageId;
    private Integer responseMessageId;
    private Boolean thinkingEnabled;
    private Boolean searchEnabled;
    private List<ChatMessage> chatMessages;

}
