package org.aiboot.service.system.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aiboot.dto.system.auth.UserLoginDTO;
import org.aiboot.dto.system.user.UserDTO;
import org.aiboot.entity.system.user.User;
import org.aiboot.vo.system.user.UserVO;

public interface AuthService extends IService<User> {
    UserVO register(UserDTO userDTO);
    UserVO signIn(UserLoginDTO loginDTO);
    void signOut();
}
