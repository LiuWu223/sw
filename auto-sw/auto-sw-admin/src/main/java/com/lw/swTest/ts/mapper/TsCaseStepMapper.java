package com.lw.swTest.ts.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCaseResult;
import com.lw.swTest.ts.entity.TsCaseStep;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 测试用例步骤表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface TsCaseStepMapper extends BaseMapper<TsCaseStep> {
    /**
     * 分页查询测试用例步骤信息
     * @param page 分页参数
     * @param stepContent 步骤内容
     * @return f
     */
    IPage<TsCaseStep> pageCaseStep(IPage page, @Param("stepContent") String stepContent);
}
