package com.lw.swTest.tol.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyDict;
import com.lw.swTest.tol.entity.TolAdb;
import com.lw.swTest.tol.mapper.TolAdbMapper;
import com.lw.swTest.tol.service.ITolAdbService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * ADB命令管理表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
@Service
public class TolAdbServiceImpl extends ServiceImpl<TolAdbMapper, TolAdb> implements ITolAdbService {
    /**
     * 通过adb名称查找adb信息
     * @param adbName 字典类型
     * @return 返回
     */
    @Override
    public List<TolAdb> selectAdbName(String adbName) {
        return baseMapper.selectAdbName(adbName);
    }
    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param adbName adb名称
     * @param adbDir adb命令
     * @return f
     */
    @Override
    public IPage<TolAdb> pageAdb(IPage page, String adbName,String adbDir) {
        return baseMapper.pageAdb(page,adbName,adbDir);
    }
}
