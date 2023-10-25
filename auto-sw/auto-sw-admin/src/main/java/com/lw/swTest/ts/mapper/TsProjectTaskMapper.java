package com.lw.swTest.ts.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsProjectTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lw.swTest.ts.entity.TsProjectTask;
import com.lw.swTest.ts.entity.TsUserProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 项目任务表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-10-06
 */
public interface TsProjectTaskMapper extends BaseMapper<TsProjectTask> {
    /**
     * 分页查询项目任务信息
     * @param page 分页参数
     * @return f
     */
    IPage<TsProjectTask> pageProjectTask(IPage page);

    /**
     * 查询项目任务
     * @param projectId 项目ID
     * @return 返回
     */
    List<TsProjectTask> selectTaskByProjectId(@Param("projectId") String projectId);

    List<TsProjectTask> selectTaskByProjectIdReNewTime(@Param("projectId") String projectId);
}
