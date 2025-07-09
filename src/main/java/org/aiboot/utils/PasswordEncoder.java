package org.aiboot.utils;

import org.springframework.util.DigestUtils;

/**
 * <p>密码编码</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
public class PasswordEncoder {

    private static String salt = "";

    public static String encodeOfMD5(String password) {
        String newPassword = salt + password;
        return DigestUtils.md5DigestAsHex(newPassword.getBytes());
    }
}
