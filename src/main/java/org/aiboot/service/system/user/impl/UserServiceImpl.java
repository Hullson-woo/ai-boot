package org.aiboot.service.system.user.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aiboot.common.exception.ServiceException;
import org.aiboot.constant.SystemConstant;
import org.aiboot.dto.system.user.UserDTO;
import org.aiboot.dto.system.user.UserQueryDTO;
import org.aiboot.entity.system.user.User;
import org.aiboot.mapper.system.user.UserMapper;
import org.aiboot.service.system.user.UserService;
import org.aiboot.utils.PasswordEncoder;
import org.aiboot.vo.system.user.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>系统用户管理实现层</p>
 *
 * @author Hullson
 * @date 2025-05-31
 * @since 1.0
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 新增系统用户
     * @param dto   UserDTO
     * @return      新增状态
     */
    @Override
    public void insert(UserDTO dto) {
        if (dto == null) {
            log.error("---- UserServiceImpl#insert 新增用户信息不能为空 ----");
            throw new ServiceException("新增用户信息不能为空");
        }
        if (StringUtils.isAnyBlank(dto.getUserName(), dto.getPassword())) {
            log.error("---- 用户账号密码不能为空 ----");
            throw new ServiceException("用户账号密码不能为空");
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);

        String password = dto.getPassword();
        String encoderPassword = PasswordEncoder.encodeOfMD5(password);
        user.setPassword(encoderPassword);
        user.preInsert();
        user.setCreateBy(user.getId());
        user.setUpdateBy(user.getId());

        baseMapper.insert(user);
    }

    /**
     * 修改系统用户
     * @param dto   UserDTO
     * @return  修改状态
     */
    @Override
    public void update(UserDTO dto) {
        if (StringUtils.isNotBlank(dto.getPassword())) {
            String password = dto.getPassword();
            String encoderPassword = PasswordEncoder.encodeOfMD5(password);
            dto.setPassword(encoderPassword);
        }

        User user = baseMapper.selectById(dto.getId());
        BeanUtils.copyProperties(dto, user);
        user.preUpdate();

        baseMapper.updateById(user);
    }

    /**
     * 删除用户
     * @param id    用户ID
     * @return      删除状态
     */
    @Override
    public void delete(String id) {
        User user = baseMapper.selectById(id);
        user.setDelFlag(SystemConstant.DEL_FLAG_DELETE);
        user.preUpdate();

        baseMapper.updateById(user);
    }

    /**
     * 根据ID获取用户信息
     * @param id    用户ID
     * @return      用户信息
     */
    @Override
    public UserVO get(String id) {
        return baseMapper.get(id);
    }

    /**
     * 用户名称是否存在
     * @param userName  用户名称
     * @return          状态
     */
    @Override
    public String hasUserName(String userName) {
        return baseMapper.hasUserName(userName);
    }

    /**
     * <p>根据用户账号密码获取用户信息</p>
     * <p>用于登录校验</p>
     * @param userName  用户账号
     * @param password  密码
     * @return          用户信息
     */
    @Override
    public UserVO loginByUserNameAndPassword(String userName, String password) {
        return baseMapper.loginByUserNameAndPassword(userName, password);
    }

    /**
     * 获取用户数据列表（分页）
     * @param queryDTO  用户查询参数
     * @return          用户数据列表
     */
    @Override
    public Page<UserVO> listPage(UserQueryDTO queryDTO) {
        Page<UserVO> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        List<UserVO> users = baseMapper.listPage(page, queryDTO);
        page.setRecords(users);

        return page;
    }
}
