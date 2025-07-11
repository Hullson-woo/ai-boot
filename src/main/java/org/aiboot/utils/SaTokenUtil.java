package org.aiboot.utils;

import com.alibaba.fastjson.JSONObject;
import org.aiboot.common.result.Result;
import org.aiboot.common.result.ResultUtil;
import org.aiboot.vo.system.user.UserVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaTokenUtil {
    private static  RedisTemplate<String, Object> redisTemplate = SpringContextHolder.getBean(RedisTemplate.class);

    public static UserVO parseToken(String token) throws IOException {
        Object tokenInfo = redisTemplate.opsForValue().get("system:user:" + token);
        if (tokenInfo == null) {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
            Result result = ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized", "请先登录");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSONObject.toJSONString(result));
        }
        return (UserVO) tokenInfo;
    }
}
