package com.lw.swTest.ts.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsTask;
import com.lw.swTest.ts.entity.TsTaskDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 测试任务详情表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface TsTaskDetailsMapper extends BaseMapper<TsTaskDetails> {
    /**
     * 分页查询任务详情信息
     * @param page 分页参数
     * @return f
     */
    IPage<TsTaskDetails> pageTaskDetails(IPage page);

    List<TsTaskDetails> selectTaskCaseByTaskId(@Param("taskId") Long taskId);
}
