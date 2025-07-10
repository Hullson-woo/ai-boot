package org.aiboot.mapper.system.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.aiboot.dto.system.user.UserQueryDTO;
import org.aiboot.entity.system.user.User;
import org.aiboot.vo.system.user.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    UserVO get(@Param("id") String id);
    String hasUserName(@Param("userName") String userName);
    UserVO loginByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);
    List<UserVO> listPage(Page<UserVO> page, @Param("queryDTO")UserQueryDTO queryDTO);
}
