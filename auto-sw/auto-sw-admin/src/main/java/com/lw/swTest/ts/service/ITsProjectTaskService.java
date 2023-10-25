package com.lw.swTest.ts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsProjectTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.swTest.ts.entity.TsProjectTask;
import com.lw.swTest.ts.entity.TsUserProject;

import java.util.List;

/**
 * <p>
 * 项目任务表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
public interface ITsProjectTaskService extends IService<TsProjectTask> {
    /**
     * 分页查询任务信息
     * @param page 分页参数
     * @return f
     */
    IPage<TsProjectTask> pageProjectTask(IPage page);

    /**
     * 查询项目任务
     * @param projectId 项目ID
     * @return 返回
     */
    List<TsProjectTask> selectTaskByProjectId(String projectId);

    List<TsProjectTask> selectTaskByProjectIdReNewTime(String projectId);
}
