package com.lw.swTest.sy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lw.swTest.system.entity.SystemUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface SyUserMapper extends BaseMapper<SyUser> {

    /**
     * 通过用户名查找用户信息
     * @param userAccount 用户账号
     * @return 返回
     */
    List<SyUser> selectUserName(@Param("userAccount") String userAccount);

    /**
     * 分页查询用户信息
     * @param page 分页参数
     * @param userName 用户名称
     * @param userAccount 用户账号
     * @return f
     */
    IPage<SyUser> pageUser(IPage page, @Param("userName") String userName, @Param("userAccount") String userAccount, @Param("roleName") String roleName);

    /**
     * 根据id查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    SyUser selectUserById(@Param("userId") Long userId);

    /**
     * 修改用户信息
     * @param syUser 用户信息
     * @return 影响行
     */
    int updateUser(SyUser syUser);

    // 新增用户
    int insertUser(SyUser syUser);

    int upUserIp(@Param("userId") Long userId,@Param("ipv") String ipv, @Param("path")  String path);

    SyUser selectByUserIp(@Param("ipv") String ipv);
}
