package org.aiboot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.TimeUnit;

/**
 * <p>SSE emitter工具</p>
 *
 * @author Hullson
 * @date 2025-06-26
 */
@Component
@Slf4j
public class SseEmitterUtil {
    private static final long DEFAULT_TIME_OUT = TimeUnit.HOURS.toMillis(24);

    public static SseEmitter create(String chatSessionId) {

        SseEmitter emitter = new SseEmitter(DEFAULT_TIME_OUT);
        log.info("---- 创建SSE实例\t{} ----", chatSessionId);

        emitter.onTimeout(() -> {
            log.error("---- SSE实例超时断开连接\t{} ----", chatSessionId);
        });

        emitter.onCompletion(() -> {
            log.info("---- SSE实例已正常断开连接\t{} ----", chatSessionId);
        });

        emitter.onError(throwable -> {
            log.error("---- SSE实例连接异常\t{}\t{} ----", chatSessionId, throwable);
        });
        return emitter;
    }

}
