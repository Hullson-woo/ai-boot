package org.aiboot.entity.chat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.aiboot.common.entity.CommonEntity;

/**
 * <p>AI工具会话</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("chat_session")
public class ChatSession extends CommonEntity {

    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 会话ID
     */
    private String chatSessionId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 会话标题
     */
    private String title;
}
