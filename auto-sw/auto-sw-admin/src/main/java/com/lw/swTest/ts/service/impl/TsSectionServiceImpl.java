package com.lw.swTest.ts.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.ts.entity.TsModule;
import com.lw.swTest.ts.entity.TsSection;
import com.lw.swTest.ts.mapper.TsSectionMapper;
import com.lw.swTest.ts.service.ITsSectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 测试项表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
@Service
public class TsSectionServiceImpl extends ServiceImpl<TsSectionMapper, TsSection> implements ITsSectionService {
    /**
     * 通过测试项名称查找测试项信息
     * @param sectionName 测试项名称
     * @return 返回
     */
    @Override
    public List<TsSection> selectSectionName(String sectionName) {
        return baseMapper.selectSectionName(sectionName);
    }
    /**
     * 分页查询测试项信息
     * @param page 分页参数
     * @param sectionName 测试项名称
     * @return f
     */
    @Override
    public IPage<TsSection> pageSection(IPage page, String sectionName) {
        return baseMapper.pageSection(page,sectionName);
    }

    @Override
    public List<TsSection> selectSectionByModuleId(Long moduleId) {
        return baseMapper.selectSectionByModuleId(moduleId);
    }
}
