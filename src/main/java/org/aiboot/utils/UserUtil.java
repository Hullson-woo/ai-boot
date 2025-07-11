package org.aiboot.utils;

import cn.dev33.satoken.stp.StpUtil;
import org.aiboot.vo.system.user.UserVO;

/**
 * <p>用户工具</p>
 *
 * @author Hullson
 * @date 2025-07-10
 */
public class UserUtil {
    public static UserVO getCurrentUser() {
        String token = StpUtil.getTokenValue();
        UserVO userVO = null;
        try {
            userVO = SaTokenUtil.parseToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userVO;
    }

    public static String getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
