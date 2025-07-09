package org.aiboot.service.system.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aiboot.common.exception.ServiceException;
import org.aiboot.constant.SystemConstant;
import org.aiboot.mapper.system.user.UserMapper;
import org.aiboot.dto.system.user.UserDTO;
import org.aiboot.dto.system.user.UserQueryDTO;
import org.aiboot.entity.system.user.User;
import org.aiboot.service.system.user.UserService;
import org.aiboot.utils.PasswordEncoder;
import org.aiboot.vo.system.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public boolean insert(UserDTO dto) {
        if (dto == null) {
            log.error("---- UserServiceImpl#insert 新增用户信息不能为空 ----");
            throw new ServiceException("新增用户信息不能为空");
        }
        if (StringUtils.isAnyBlank(dto.getUserName(), dto.getPassword())) {
            log.error("---- UserServiceImpl#insert 用户账号密码不能为空 ----");
            throw new ServiceException("用户账号密码不能为空");
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);

        String password = dto.getPassword();
        String encoderPassword = PasswordEncoder.encodeOfMD5(password);
        user.setPassword(encoderPassword);
        user.preInsert();

        boolean result = false;
        int row = baseMapper.insert(user);
        if (row > 0) {
            result = true;
        }
        return result;
    }

    /**
     * 修改系统用户
     * @param dto   UserDTO
     * @return  修改状态
     */
    @Override
    public boolean update(UserDTO dto) {
        if (StringUtils.isNotBlank(dto.getPassword())) {
            String password = dto.getPassword();
            String encoderPassword = PasswordEncoder.encodeOfMD5(password);
            dto.setPassword(encoderPassword);
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.preUpdate();

        boolean result = false;
        int row = baseMapper.updateById(user);
        if (row > 0) {
            result = true;
        }

        return result;
    }

    /**
     * 删除用户
     * @param id    用户ID
     * @return      删除状态
     */
    @Override
    public boolean delete(String id) {
        User user = baseMapper.selectById(id);
        user.setDelFlag(SystemConstant.DEL_FLAG_DELETE);
        user.preUpdate();

        boolean result = false;
        int row = baseMapper.updateById(user);
        if (row > 0) {
            result = true;
        }
        return result;
    }

    /**
     * 根据ID获取用户信息
     * @param id    用户ID
     * @return      用户信息
     */
    @Override
    public UserVO get(String id) {
        User user = baseMapper.selectById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 获取用户数据列表（分页）
     * @param queryDTO  UserQueryDTO
     * @return          用户数据列表
     */
    @Override
    public Page<UserVO> listPage(UserQueryDTO queryDTO) {
        if (queryDTO == null) {
            return new Page<>();
        }

        Page<User> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getDelFlag, SystemConstant.DEL_FLAG_NORMAL)
                .eq(StringUtils.isNotBlank(queryDTO.getUserName()), User::getUserName, queryDTO.getUserName())
                .orderByDesc(User::getCreateDate);
        page = baseMapper.selectPage(page, wrapper);

        Page<UserVO> voPage = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        BeanUtils.copyProperties(page, voPage, "records");

        List<UserVO> vos = page.getRecords().stream()
                .map(entity -> {
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(entity, userVO);
                    return userVO;
                }).collect(Collectors.toList());
        voPage.setRecords(vos);

        return voPage;
    }
}
