package org.aiboot.vo.system.vo;

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
    private String userName;
    private String createBy;
    private Date createDate;
}
