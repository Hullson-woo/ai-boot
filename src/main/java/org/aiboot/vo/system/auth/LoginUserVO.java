package org.aiboot.vo.system.auth;

import lombok.Data;
import org.aiboot.vo.system.user.UserVO;

/**
 * <p>用户管理-登录用户信息</p>
 *
 * @author Hullson
 * @date 2025-07-10
 */
@Data
public class LoginUserVO {
    private String token;
    private UserVO user;
}
