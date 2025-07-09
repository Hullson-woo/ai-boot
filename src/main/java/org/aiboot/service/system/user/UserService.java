package org.aiboot.service.system.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aiboot.dto.system.user.UserDTO;
import org.aiboot.dto.system.user.UserQueryDTO;
import org.aiboot.entity.system.user.User;
import org.aiboot.vo.system.vo.UserVO;

public interface UserService extends IService<User> {
    boolean insert(UserDTO dto);
    boolean update(UserDTO dto);
    boolean delete(String id);

    UserVO get(String id);
    Page<UserVO> listPage(UserQueryDTO queryDTO);
}
