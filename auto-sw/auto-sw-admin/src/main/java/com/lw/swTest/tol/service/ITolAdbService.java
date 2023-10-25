package com.lw.swTest.tol.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyDict;
import com.lw.swTest.tol.entity.TolAdb;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * ADB命令管理表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
public interface ITolAdbService extends IService<TolAdb> {
    /**
     * 通过adb名称查找adb信息
     * @param adbName 字典类型
     * @return 返回
     */
    List<TolAdb> selectAdbName(String adbName);
    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param adbName adb名称
     * @param adbDir adb命令
     * @return f
     */
    IPage<TolAdb> pageAdb(IPage page, String adbName,String adbDir);
}
