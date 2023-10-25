package com.lw.swTest.ts.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsCase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lw.swTest.ts.entity.TsModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 测试用例表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface TsCaseMapper extends BaseMapper<TsCase> {
    /**
     * 通过测试用例名称查找测试用例信息信息
     * @param caseName 测试用例名称
     * @return 返回
     */
    List<TsCase> selectCaseName(@Param("caseName") String caseName);

    /**
     * 分页查询测试用例信息
     * @param page 分页参数
     * @param caseName 测试用例名称
     * @return f
     */
    IPage<TsCase> pageCase(IPage page, @Param("caseName") String caseName);


    List<TsCase> selectCaseBySectionId(@Param("sectionId") Long sectionId);
}
