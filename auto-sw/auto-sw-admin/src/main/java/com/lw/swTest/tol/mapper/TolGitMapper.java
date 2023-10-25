package com.lw.swTest.tol.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.tol.entity.TolGit;
import com.lw.swTest.tol.entity.TolGit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * git项目管理表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
public interface TolGitMapper extends BaseMapper<TolGit> {
    /**
     * 通过git名称查找git信息
     * @param gitName gitName
     * @return 返回
     */
    List<TolGit> selectGitName(@Param("gitName") String gitName);

    /**
     * 分页查询git信息
     * @param page 分页参数
     * @param gitName gitName
     * @return f
     */
    IPage<TolGit> pageGit(IPage page, @Param("gitName") String gitName);
}
