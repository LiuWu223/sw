package com.lw.swTest.ts.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.py.entity.PyFlask;
import com.lw.swTest.ts.entity.TsModule;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 测试用例模块表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ITsModuleService extends IService<TsModule> {
    /**
     * 通过module名称查找module信息
     * @param moduleName moduleName
     * @return 返回
     */
    List<TsModule> selectModuleName(String moduleName);
    /**
     * 分页查询测试用例信息
     * @param page 分页参数
     * @param moduleName flaskName
     * @return f
     */
    IPage<TsModule> pageModule(IPage page, String moduleName);

    List<TsModule> selectModules();
}
