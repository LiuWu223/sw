package com.lw.swTest.ts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCase;
import com.lw.swTest.ts.entity.TsUserProject;
import com.lw.swTest.ts.entity.TsUserProject;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户项目表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
public interface ITsUserProjectService extends IService<TsUserProject> {
    /**
     * 分页查询用户项目信息
     * @param page 分页参数
     * @return f
     */
    IPage<TsUserProject> pageUserProject(IPage page);

    /**
     * 查询用户项目
     * @param userId 用户ID
     * @return 返回
     */
    List<TsUserProject> selectProjectByUserId(String userId);

    int removeUserProjectByProjectId(String projectId);
}
