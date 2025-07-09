package org.aiboot.dto.system.user;

import lombok.Data;

/**
 * <p>系统用户管理</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@Data
public class UserDTO {
    private String id;
    private String userName;
    private String password;
}
