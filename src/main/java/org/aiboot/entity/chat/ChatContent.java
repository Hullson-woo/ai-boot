package org.aiboot.entity.chat;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.aiboot.common.entity.DataEntity;

import java.math.BigDecimal;

/**
 * <p>AI工具消息</p>
 *
 * @author Hullson
 * @date 2025-06-01
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("chat_content")
public class ChatContent extends DataEntity {
    /**
     * 会话id
     */
    private String chatSessionId;

    /**
     * 消息id
     */
    private Integer messageId;

    /**
     * 上一次消息id
     */
    private Integer parentMessageId;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息信号
     */
    private String messageSignal;

    /**
     * 消息角色
     */
    private String messageRole;

    /**
     * 使用模型
     */
    private String model;

    /**
     * 提示词背景 [system]
     */
    private String systemSetup;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 深度思考内容
     */
    private String thinkingContent;

    /**
     * 是否开启深度思考
     */
    private Boolean thinkingEnabled;

    /**
     * 是否开启联网搜索
     */
    private Boolean searchEnabled;

    /**
     * token使用量
     */
    private Integer tokenUsage;

    /**
     * token花费
     */
    private BigDecimal tokenCost;

    /**
     * 文档路径
     */
    private String docUrl;

    /**
     * 图片路径
     */
    private String imageUrl;
}
