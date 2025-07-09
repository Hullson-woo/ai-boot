package org.aiboot.controller.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * <p>对话控制层</p>
 *
 * @author Hullson
 * @date 2025-06-26
 */
@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {
    @GetMapping("message")
    public SseEmitter message(@RequestBody String message) {
        return null;
//        String apiKey = "9c031c6a-d239-4385-a598-b8a07e1bd67e";
//        ArkService service = ArkService.builder().apiKey(apiKey).build();
//        final List<ChatContent> messages = new ArrayList<>();
//        final ChatContent systemMessage = ChatContent.builder().role(ChatMessageRole.SYSTEM).content("你是DEEPSEEK").build();
//        final ChatContent userMessage = ChatContent.builder().role(ChatMessageRole.USER).content(message).build();
//        messages.add(systemMessage);
//        messages.add(userMessage);
//
//        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
////                .model("deepseek-v3-250324")
//                .model("deepseek-r1-250528")
//                .messages(messages)
//                .stream(true)
//                .build();
//        service.streamChatCompletion(chatCompletionRequest)
//                .doOnError(Throwable::printStackTrace)
//                .blockingForEach(choice -> {
//                    if (choice.getChoices().size() > 0) {
//                        ChatContent chatMessage = choice.getChoices().get(0).getMessage();
//                        if (chatMessage.getReasoningContent() != null && !chatMessage.getReasoningContent().isEmpty()) {
//                            emitter.send(SseEmitter.event()
//                                        .name("reasoningMessage")
//                                        .data(chatMessage.getReasoningContent()));
//                        } else {
//                            emitter.send(SseEmitter.event()
//                                    .name("message")
//                                    .data(chatMessage.getContent()));
//                        }
//                    }
//                });
//        emitter.complete();
//
//        // shutdown service
//        service.shutdownExecutor();
//        return emitter;
    }
}
