package org.aiboot.controller.system.health;

import org.aiboot.common.result.Result;
import org.aiboot.common.result.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>服务健康状态检查</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@RestController
@RequestMapping("/system/health")
public class HealthController {

    @GetMapping("check")
    public Result check() {
        return ResultUtil.success();
    }
}
