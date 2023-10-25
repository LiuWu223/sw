package com.lw.swTest.sy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyAuthority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lw.swTest.sy.entity.SyRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface SyAuthorityMapper extends BaseMapper<SyAuthority> {
    /**
     * 通过权限名称查找权限信息
     * @param authorityName 权限名称
     * @return 返回
     */
    List<SyAuthority> selectAuthorityName(@Param("authorityName") String authorityName);

    /**
     * 分页查询角色信息
     * @param page 分页参数
     * @param authorityName 角色名称
     * @return f
     */
    IPage<SyAuthority> pageAuthority(IPage page, @Param("authorityName") String authorityName);
}
