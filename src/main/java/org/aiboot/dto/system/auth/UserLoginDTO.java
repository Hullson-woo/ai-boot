package org.aiboot.dto.system.auth;

import lombok.Data;

/**
 * <p>用户登录参数</p>
 *
 * @author Hullson
 * @date 2025-07-09
 */
@Data
public class UserLoginDTO {
    private String userName;
    private String password;
}
