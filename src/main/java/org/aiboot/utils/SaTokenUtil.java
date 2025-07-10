package org.aiboot.utils;

import org.aiboot.common.exception.ServiceException;
import org.aiboot.vo.system.user.UserVO;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletResponse;

public class SaTokenUtil {
    private static  RedisTemplate<String, Object> redisTemplate = SpringContextHolder.getBean(RedisTemplate.class);

    public static UserVO parseToken(String headerToken) {
        String token = headerToken.split("Bearer ")[1];
        Object tokenInfo = redisTemplate.opsForValue().get("system:user:" + token);
        if (tokenInfo == null) {
            throw new ServiceException(HttpServletResponse.SC_UNAUTHORIZED, "请先登录");
        }
        return (UserVO) tokenInfo;
    }
}
