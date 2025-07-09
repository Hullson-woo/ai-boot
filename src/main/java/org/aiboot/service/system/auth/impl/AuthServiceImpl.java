package org.aiboot.service.system.auth.impl;

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
import org.aiboot.vo.system.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>权限管理-权限认证</p>
 *
 * @author Hullson
 * @date 2025-07-09
 */
@Service
@Slf4j
public class AuthServiceImpl extends ServiceImpl<UserMapper, User> implements AuthService {
    @Autowired
    private UserService userService;
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
    public UserVO signIn(UserLoginDTO loginDTO) {
        String userName = loginDTO.getUserName();
        String password = loginDTO.getPassword();
        String encoderPassword = PasswordEncoder.encodeOfMD5(password);

        boolean hasUserName = userService.hasUserName(userName);
        if (!hasUserName) {
            log.error("---- 用户账号不存在 {} ----", userName);
            throw new ServiceException("账号不存在，请确认后重新输入");
        }

        UserVO loginUser = userService.loginByUserNameAndPassword(userName, encoderPassword);
        if (loginUser == null) {
            log.error("---- 用户账号密码错误 {} ----", userName);
            throw new ServiceException("用户账号密码错误");
        }

        return null;
    }

    @Override
    public void signOut() {

    }
}
