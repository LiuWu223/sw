package com.lw.swTest.ts.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCase;
import com.lw.swTest.ts.entity.TsCaseResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 测试用例预期表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface TsCaseResultMapper extends BaseMapper<TsCaseResult> {
    /**
     * 分页查询测试用例预期结果信息
     * @param page 分页参数
     * @param resultContent 预期结果内容
     * @return f
     */
    IPage<TsCaseResult> pageCaseResult(IPage page, @Param("resultContent") String resultContent);
}
