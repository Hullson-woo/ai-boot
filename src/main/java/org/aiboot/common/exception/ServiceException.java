package org.aiboot.common.exception;

import lombok.Data;

/**
 * <p>自定义业务异常处理</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@Data
public class ServiceException extends RuntimeException {
    private Integer code;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
