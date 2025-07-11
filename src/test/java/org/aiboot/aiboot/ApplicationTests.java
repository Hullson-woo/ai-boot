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
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("你是什么模型").build();
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("moonshot-v1-32k")
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
    }

}
