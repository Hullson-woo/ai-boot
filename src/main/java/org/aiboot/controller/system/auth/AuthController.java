package org.aiboot.controller.system.auth;

import org.aiboot.common.result.Result;
import org.aiboot.common.result.ResultUtil;
import org.aiboot.dto.system.auth.UserLoginDTO;
import org.aiboot.service.system.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>认证管理</p>
 *
 * @author Hullson
 * @date 2025-07-10
 */
@RestController
@RequestMapping("/system/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("signIn")
    public Result signIn(@RequestBody UserLoginDTO loginDTO) {
        return ResultUtil.success(authService.signIn(loginDTO));
    }
}
