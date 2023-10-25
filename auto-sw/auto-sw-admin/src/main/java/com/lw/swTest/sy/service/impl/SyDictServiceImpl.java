package com.lw.swTest.sy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyDict;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.mapper.SyDictMapper;
import com.lw.swTest.sy.service.ISyDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class SyDictServiceImpl extends ServiceImpl<SyDictMapper, SyDict> implements ISyDictService {
    /**
     * 查找字典type
     * @param dictType 字典类型
     * @return 返
     */
    @Override
    public List<SyDict> selectDictType(String dictType) {
        return baseMapper.selectDictType(dictType);
    }
    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param dictType 字典类型
     * @return f
     */
    @Override
    public IPage<SyDict> pageDict(IPage page, String dictType) {
        return baseMapper.pageDict(page,dictType);
    }

    @Override
    public SyDict selectDict(String dictContent) {
        return baseMapper.selectDict(dictContent);
    }
}
