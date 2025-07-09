package org.aiboot.entity.system.user;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.aiboot.common.entity.DataEntity;

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

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像地址
     */
    private String avatarUrl;

    /**
     * 性别（0-保密；1-男；2-女）
     */
    private String gender;

    /**
     * 微信登录id
     */
    private String wechatAppId;

    /**
     * 手机号码
     */
    private String phone;
}
