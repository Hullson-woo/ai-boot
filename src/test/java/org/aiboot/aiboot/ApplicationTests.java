package org.aiboot.aiboot;

import com.alibaba.fastjson.JSONObject;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        String apiKey = "9c031c6a-d239-4385-a598-b8a07e1bd67e";
        ArkService service = ArkService.builder().apiKey(apiKey).build();
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("常见的十字花科植物有哪些？").build();
        messages.add(systemMessage);
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("deepseek-v3-250324")
                .messages(messages)
//                .thinking(new ChatCompletionRequest.ChatCompletionRequestThinking("disabled"))
                .build();
        service.streamChatCompletion(chatCompletionRequest)
                .doOnError(Throwable::printStackTrace)
                        .blockingForEach(choice -> {
                            if (choice.getChoices().size() > 0) {
                                ChatMessage message = choice.getChoices().get(0).getMessage();
                                System.out.println((message.getContent() instanceof String)
                                        ? (String) message.getContent()
                                        : JSONObject.toJSONString(message.getContent()));
                            }
                        });

        // shutdown service
        service.shutdownExecutor();

//        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
//                .model("deepseek-r1-250528")
//                .messages(messages)
//                .stream(true)
//                .build();
//        service.createChatCompletion(chatCompletionRequest).getChoices().forEach(choice -> System.out.println(choice.getMessage().getContent()));
//        // shutdown service
//        service.shutdownExecutor();
    }

}
