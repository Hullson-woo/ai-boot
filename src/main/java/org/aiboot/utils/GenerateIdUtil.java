package org.aiboot.utils;

import java.util.UUID;

/**
 * <p>ID 生成工具类</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
public class GenerateIdUtil {

    public static String uuidGenerator() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
