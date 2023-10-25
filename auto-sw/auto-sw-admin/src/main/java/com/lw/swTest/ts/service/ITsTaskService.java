package com.lw.swTest.ts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsModule;
import com.lw.swTest.ts.entity.TsTask;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 测试任务表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ITsTaskService extends IService<TsTask> {
    /**
     * 通过任务名称查找任务信息
     * @param taskName 任务名称
     * @return 返回
     */
    List<TsTask> selectTaskName(String taskName);
    /**
     * 分页查询任务信息
     * @param page 分页参数
     * @param taskName 任务名称
     * @return f
     */
    IPage<TsTask> pageTask(IPage page, String taskName);

    TsTask getTaskTimiTNoe();

    TsTask selectTaskById(Long taskId);
}
