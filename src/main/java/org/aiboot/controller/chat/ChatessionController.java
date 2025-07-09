package org.aiboot.controller.chat;

import org.aiboot.common.result.Result;
import org.aiboot.common.result.ResultUtil;
import org.aiboot.service.chat.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>AI工具会话</p>
 *
 * @author Hullson
 * @date 2025-06-01
 * @since 1.0
 */
@RestController
@RequestMapping("/chat/session")
public class ChatessionController {

    @Autowired
    private ChatSessionService service;

    @GetMapping("create")
    public Result create() {
        return ResultUtil.success(service.create());
    }

    @PutMapping("rename")
    public Result rename(@RequestParam("chatSessionId") String chatSessionId,
                         @RequestParam("title") String title) {
        service.rename(chatSessionId, title);
        return ResultUtil.success();
    }

    @DeleteMapping("delete")
    public Result delete(@RequestParam("chatSessionId") String chatSessionId) {
        service.delete(chatSessionId);
        return ResultUtil.success("删除成功");
    }

    @GetMapping("listHistory")
    public Result listHistory() {
        return ResultUtil.success(service.listHistory());
    }
}
