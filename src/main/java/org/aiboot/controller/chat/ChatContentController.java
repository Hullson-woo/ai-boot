package org.aiboot.controller.chat;

import org.aiboot.common.result.Result;
import org.aiboot.common.result.ResultUtil;
import org.aiboot.dto.chat.ChatContentDTO;
import org.aiboot.dto.chat.ChatRegenerateDTO;
import org.aiboot.service.chat.ChatContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

/**
 * <p>AI 对话</p>
 *
 * @author Hullson
 * @date 2025-06-27
 */
@RestController
@RequestMapping("/chat")
public class ChatContentController {
    @Autowired
    private ChatContentService chatContentService;

    @PostMapping("communication")
    public SseEmitter communication(@RequestBody ChatContentDTO chatContentDTO) throws IOException {
        return chatContentService.communication(chatContentDTO);
    }

    @GetMapping("stop/{chatSessionId}")
    public Result stop(@PathVariable("chatSessionId") String chatSessionId) {
        chatContentService.stopStream(chatSessionId);
        return ResultUtil.success();
    }

    @PostMapping("regenerate")
    public SseEmitter regenerate(@RequestBody ChatRegenerateDTO regenerateDTO) {
        return chatContentService.regenerate(regenerateDTO);
    }

    @GetMapping("list")
    public Result list(@RequestParam("chatSessionId") String chatSessionId) {
        return ResultUtil.success(chatContentService.list(chatSessionId));
    }
}
