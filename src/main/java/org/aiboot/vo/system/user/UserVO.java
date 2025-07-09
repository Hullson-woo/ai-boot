package org.aiboot.vo.system.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * <p>系统用户管理</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@Data
public class UserVO {
    private String id;
    private String nickname;
    private String avatarUrl;
    private String gender;
    private String createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
}
