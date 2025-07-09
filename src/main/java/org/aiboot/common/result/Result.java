package org.aiboot.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * <p>统一返回结果数据结构</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Result<T> implements Serializable {
    /**
     * 状态码
     */
    private Integer code = HttpStatus.OK.value();

    /**
     * <p>提示信息</p>
     */
    private String message;

    /**
     * 异常信息
     */
    private String error;

    /**
     * 数据对象
     */
    private T data;
}