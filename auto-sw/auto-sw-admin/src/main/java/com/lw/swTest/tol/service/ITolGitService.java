package com.lw.swTest.tol.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.tol.entity.TolGit;
import com.lw.swTest.tol.entity.TolGit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * git项目管理表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
public interface ITolGitService extends IService<TolGit> {
    /**
     * 通过git名称查找git信息
     * @param gitName 字典类型
     * @return 返回
     */
    List<TolGit> selectGitName(String gitName);
    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param gitName git名称
     * @return f
     */
    IPage<TolGit> pageGit(IPage page, String gitName);
}
