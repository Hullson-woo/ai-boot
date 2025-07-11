package org.aiboot.common;

import lombok.extern.slf4j.Slf4j;
import org.aiboot.common.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>AI对话模型工具</p>
 *
 * @author Hullson
 * @date 2025-06-30
 */
@Component
@Slf4j
public class AiModelUtil {
    @Value("${ai.deepseek.model-v3-id}")
    private String deepseekV3;
    @Value("${ai.deepseek.model-r1-id}")
    private String deepseekR1;
    @Value("${ai.kimi.model-id}")
    private String kimiV1;

    public String matchAiModel(String model, Boolean thinkingEnable) {
        String executorModel;
        switch (model) {
            case "deepseek":
                executorModel = thinkingEnable ? deepseekR1 : deepseekV3;
                break;
            case "kimi":
                executorModel = kimiV1;
                break;
            default:
                log.error("---- AiModelUtil#matchAiModel 未匹配到AI调用模型\n 模型名称：{}\t是否开启深度思考：{} ----", model, thinkingEnable);
                throw new ServiceException("未匹配AI调用模型");
        }

        return executorModel;
    }
}
