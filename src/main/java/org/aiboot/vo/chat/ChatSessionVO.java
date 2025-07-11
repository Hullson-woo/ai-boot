package org.aiboot.vo.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
}
