package org.aiboot.entity.system.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.aiboot.common.DataEntity;

/**
 * <p>系统用户</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "sys_user")
public class User extends DataEntity {
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;
}
