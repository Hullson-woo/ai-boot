package org.aiboot.controller.system.user;

import org.aiboot.common.result.Result;
import org.aiboot.common.result.ResultUtil;
import org.aiboot.dto.system.user.UserDTO;
import org.aiboot.dto.system.user.UserQueryDTO;
import org.aiboot.service.system.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>系统用户管理</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("insert")
    public Result insert(@RequestBody UserDTO userDTO) {
        try {
            if (userService.insert(userDTO)) {
                return ResultUtil.success("新增成功");
            } else {
                return ResultUtil.error("新增失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("新增失败");
        }
    }

    @PutMapping("update")
    public Result update(@RequestBody UserDTO userDTO) {
        try {
            if (userService.update(userDTO)) {
                return ResultUtil.success("修改成功");
            } else {
                return ResultUtil.error("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("修改失败");
        }
    }

    @DeleteMapping("delete/{id}")
    public Result deete(@PathVariable("id") String id) {
        try {
            if (userService.delete(id)) {
                return ResultUtil.success("删除成功");
            } else {
                return ResultUtil.error("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error("删除失败");
        }
    }

    @GetMapping("get")
    public Result get(@RequestParam("id") String id) {
        return ResultUtil.success(userService.get(id));
    }

    @PostMapping("listPage")
    public Result listPage(@RequestBody UserQueryDTO queryDTO) {
        return ResultUtil.success(userService.listPage(queryDTO));
    }
}
