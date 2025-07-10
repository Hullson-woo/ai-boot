package org.aiboot.service.system.auth.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aiboot.common.exception.ServiceException;
import org.aiboot.dto.system.auth.UserLoginDTO;
import org.aiboot.dto.system.user.UserDTO;
import org.aiboot.entity.system.user.User;
import org.aiboot.mapper.system.user.UserMapper;
import org.aiboot.service.system.auth.AuthService;
import org.aiboot.service.system.user.UserService;
import org.aiboot.utils.PasswordEncoder;
import org.aiboot.vo.system.auth.LoginUserVO;
import org.aiboot.vo.system.user.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * <p>权限管理-权限认证</p>
 *
 * @author Hullson
 * @date 2025-07-09
 */
@Service
@Slf4j
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {
    @Value("${auth.prefix-key}")
    private String prefixKey;
    @Value("${auth.timeout}")
    private Duration timeout;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 注册用户账号
     * @param userDTO 用户信息参数
     * @return        用户信息
     */
    @Override
    public UserVO register(UserDTO userDTO) {
        return null;
    }

    /**
     * 登录
     * @param loginDTO  用户登录参数
     * @return          用户信息
     */
    @Override
    public LoginUserVO signIn(UserLoginDTO loginDTO) {
        String userName = loginDTO.getUserName();
        String password = loginDTO.getPassword();
        String encoderPassword = PasswordEncoder.encodeOfMD5(password);

        String hasUserId = userService.hasUserName(userName);
        if (StringUtils.isBlank(hasUserId)) {
            log.error("---- 用户账号不存在 {} ----", userName);
            throw new ServiceException("账号不存在，请确认后重新输入");
        }

        UserVO loginUser = userService.loginByUserNameAndPassword(userName, encoderPassword);
        if (loginUser == null) {
            log.error("---- 用户账号密码错误 {} ----", userName);
            throw new ServiceException("用户账号密码错误");
        }
        String id = loginUser.getId();
        StpUtil.login(id);

        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        String token = tokenInfo.getTokenValue();
        redisTemplate.opsForValue().set(prefixKey + token, loginUser, timeout);

        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setToken(token);
        loginUserVO.setUser(loginUser);
        return loginUserVO;
    }

    @Override
    public void signOut() {

    }
}
