package org.aiboot.common.result;

import org.springframework.http.HttpStatus;

/**
 * <p>结果返回工具类</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
public class ResultUtil {
    public static <T>Result success() {
        return success("", null);
    }

    public static <T>Result success(String message) {
        return success(message, null);
    }

    public static <T>Result success(T data) {
        return success("", data);
    }

    /**
     * 成功结果返回
     * @param message   提示信息
     * @param data      结果实体类
     * @return          Result
     * @param <T>
     */
    public static <T>Result success(String message, T data) {
        Result result = new Result();
        result.setCode(HttpStatus.OK.value())
                .setMessage(message)
                .setData(data);
        return result;
    }

    public static <T>Result error(Integer code) {
        return error(code, null, null);
    }

    public static <T>Result error(String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, message);
    }

    public static <T>Result error(Integer code, String error) {
        return error(code, error, null);
    }

    public static <T>Result error(String error, String message) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), error, message);
    }

    /**
     * 失败结果返回
     * @param code          失败状态码
     * @param error         错误信息
     * @param message       提示信息
     * @return              Result
     * @param <T>
     */
    public static <T>Result error(Integer code, String error, String message) {
        Result result = new Result();
        result.setCode(code)
                .setMessage(message)
                .setError(error);
        return result;
    }

    public static <T>Result returnCode(Integer code) {
        return returnCode(code, "", null);
    }

    public static <T>Result returnCode(Integer code, String message) {
        return returnCode(code, message, null);
    }

    public static <T>Result returnCode(Integer code, T data) {
        return returnCode(code, "", data);
    }

    /**
     * 统一封装结果返回
     * @param code      结果返回状态码
     * @param message   提示信息
     * @param data      返回结果实体
     * @return          Result
     * @param <T>
     */
    public static <T>Result returnCode(Integer code, String message, T data) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

}
