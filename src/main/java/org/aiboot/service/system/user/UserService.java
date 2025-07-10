package org.aiboot.service.system.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aiboot.dto.system.user.UserDTO;
import org.aiboot.dto.system.user.UserQueryDTO;
import org.aiboot.entity.system.user.User;
import org.aiboot.vo.system.user.UserVO;

public interface UserService extends IService<User> {
    void insert(UserDTO dto);
    void update(UserDTO dto);
    void delete(String id);

    UserVO get(String id);
    String hasUserName(String userName);
    UserVO loginByUserNameAndPassword(String userName, String password);
    Page<UserVO> listPage(UserQueryDTO queryDTO);
}
