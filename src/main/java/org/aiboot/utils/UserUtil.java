package org.aiboot.utils;

import org.aiboot.vo.system.user.UserVO;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>用户工具</p>
 *
 * @author Hullson
 * @date 2025-07-10
 */
public class UserUtil {
    public static UserVO getCurrentUser() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String token = request.getHeader("Authorization");
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
