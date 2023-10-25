package com.lw.swTest.sy.mapper;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyUser;
import com.lw.swTest.sy.entity.SyUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface SyUserRoleMapper extends BaseMapper<SyUserRole> {
    /**
     * 分页查询用户角色信息
     * @param page 分页参数
     * @param userName 用户名称
     * @param roleName 角色名称
     * @return f
     */
    IPage<SyUserRole> pageUserRole(IPage page, @Param("userName") String userName, @Param("roleName") String roleName);

    /**
     * 通过用户ID查询用户角色ID
     * @param userId 用户ID
     * @return 用户角色ID
     */
    SyUserRole selectUserIdByRoleId(@Param("userId") Long userId);

    /**
     * 修改用户角色信息
     * @param syUserRole 用户角色信息
     * @return 影响行
     */
    int updateUserRole(SyUserRole syUserRole);

    int insertUserRole(SyUserRole syUserRole);

    int removeUserRoleByUserId(@Param("userId") Long userId);
}
