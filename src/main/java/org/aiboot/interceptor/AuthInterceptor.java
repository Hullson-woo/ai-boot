package org.aiboot.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import org.aiboot.common.result.Result;
import org.aiboot.common.result.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>权限拦截器</p>
 *
 * @author Hullson
 * @date 2025-07-10
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Value("${auth.prefix-key}")
    private String prefixKey;
    @Value("${auth.token-name}")
    private String tokenName;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 获取请求头中的token
        String headerToken = request.getHeader(tokenName);

        if (headerToken == null || !headerToken.startsWith("Bearer ")) {
            Result result = ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized", "请先登录");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSONObject.toJSONString(result));
            return false;
        }

        // 通过SaToken校验是否登录
        try {
            StpUtil.checkLogin();
        } catch (Exception e) {
            Result result = ResultUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized", "请先登录");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSONObject.toJSONString(result));
            return false;
        }

        return true;
    }
}
