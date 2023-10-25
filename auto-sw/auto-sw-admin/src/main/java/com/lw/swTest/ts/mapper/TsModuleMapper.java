package com.lw.swTest.ts.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.py.entity.PyFlask;
import com.lw.swTest.ts.entity.TsModule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 测试用例模块表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface TsModuleMapper extends BaseMapper<TsModule> {
    /**
     * 通过module名称查找module信息
     * @param moduleName moduleName
     * @return 返回
     */
    List<TsModule> selectModuleName(@Param("moduleName") String moduleName);

    /**
     * 分页查询模块信息
     * @param page 分页参数
     * @param moduleName flaskName
     * @return f
     */
    IPage<TsModule> pageModule(IPage page, @Param("moduleName") String moduleName);

    List<TsModule> selectModules();
}
