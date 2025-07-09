package org.aiboot.dto.system.user;

import lombok.Data;

/**
 * <p>用户查询</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@Data
public class UserQueryDTO {
    private String userName;

    private Integer pageNum;
    private Integer pageSize;
}
