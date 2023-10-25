package com.lw.swTest.ts.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.py.entity.PyFlask;
import com.lw.swTest.ts.entity.TsModule;
import com.lw.swTest.ts.mapper.TsModuleMapper;
import com.lw.swTest.ts.service.ITsModuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 测试用例模块表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class TsModuleServiceImpl extends ServiceImpl<TsModuleMapper, TsModule> implements ITsModuleService {
    /**
     * 通过module名称查找module信息
     * @param moduleName moduleName
     * @return 返回
     */
    @Override
    public List<TsModule> selectModuleName(String moduleName) {
        return baseMapper.selectModuleName(moduleName);
    }
    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param moduleName flaskName
     * @return f
     */
    @Override
    public IPage<TsModule> pageModule(IPage page, String moduleName) {
        return baseMapper.pageModule(page,moduleName);
    }

    @Override
    public List<TsModule> selectModules() {
        return baseMapper.selectModules();
    }
}
