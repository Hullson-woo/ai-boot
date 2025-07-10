package org.aiboot.controller.system.test;

import cn.dev33.satoken.stp.StpUtil;
import org.aiboot.common.result.Result;
import org.aiboot.common.result.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>測試接口</p>
 *
 * @author Hullson
 * @date 2025-07-10
 */
@RestController
@RequestMapping("/system/test")
public class TestController {

    @GetMapping("saveToken")
    public Result saveToken() {
        StpUtil.checkLogin();
        String id = StpUtil.getLoginIdAsString();
        return ResultUtil.success(id);
    }
}
