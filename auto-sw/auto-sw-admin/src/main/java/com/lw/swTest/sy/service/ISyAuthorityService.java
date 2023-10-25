package com.lw.swTest.sy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyAuthority;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.swTest.sy.entity.SyRole;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ISyAuthorityService extends IService<SyAuthority> {
    /**
     * 通过权限名称查找权限信息
     * @param authorityName 权限名称
     * @return 返回
     */
    List<SyAuthority> selectAuthorityName(String authorityName);

    /**
     * 分页查询角色信息
     * @param page 分页参数
     * @param authorityName 权限名称
     * @return f
     */
    IPage<SyAuthority> pageAuthority(IPage page, String authorityName);
}
