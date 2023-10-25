package com.lw.swTest.ts.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsModule;
import com.lw.swTest.ts.entity.TsTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 测试任务表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface TsTaskMapper extends BaseMapper<TsTask> {
    /**
     * 通过任务名称查找任务信息
     * @param taskName 任务名称
     * @return 返回
     */
    List<TsTask> selectTaskName(@Param("taskName") String taskName);

    /**
     * 分页查询任务信息
     * @param page 分页参数
     * @param taskName 任务名称
     * @return f
     */
    IPage<TsTask> pageTask(IPage page, @Param("taskName") String taskName);

    TsTask getTaskTimiTNoe();

    TsTask selectTaskById(@Param("taskId") Long taskId);

}
