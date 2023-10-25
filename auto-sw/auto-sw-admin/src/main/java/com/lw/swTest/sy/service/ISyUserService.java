package com.lw.swTest.sy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ISyUserService extends IService<SyUser> {
    /**
     * 查找用户名
     * @param userAccount 用户账号
     * @return 返
     */
    List<SyUser> selectUserName(String userAccount);

    /**
     * 分页查询用户信息
     * @param page 分页参数
     * @param userName 用户名称
     * @param userAccount 用户账号
     * @return f
     */
    IPage<SyUser> pageUser(IPage page, String userName, String userAccount, String roleName);

    /**
     * 根据id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    SyUser selectUserById(Long userId);

    int updateUser(SyUser syUser);

    int insertUser(SyUser syUser);

    int upUserIp(Long userId, String ipv, String path);

    SyUser selectByUserIp(String ipv);
}
