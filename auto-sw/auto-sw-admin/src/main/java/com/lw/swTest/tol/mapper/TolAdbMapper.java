package com.lw.swTest.tol.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyDict;
import com.lw.swTest.tol.entity.TolAdb;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ADB命令管理表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
public interface TolAdbMapper extends BaseMapper<TolAdb> {
    /**
     * 通过adb名称查找adb信息
     * @param adbName 字典类型
     * @return 返回
     */
    List<TolAdb> selectAdbName(@Param("adbName") String adbName);

    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param adbName adb名称
     * @param adbDir adb命令
     * @return f
     */
    IPage<TolAdb> pageAdb(IPage page, @Param("adbName") String adbName,@Param("adbDir") String adbDir);
}
