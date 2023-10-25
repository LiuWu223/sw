package com.lw.swTest.sy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyDict;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.swTest.sy.entity.SyRole;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ISyDictService extends IService<SyDict> {
    /**
     * 查找字典type
     * @param dictType 字典类型
     * @return 返
     */
    List<SyDict> selectDictType(String dictType);
    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param dictType 字典类型
     * @return f
     */
    IPage<SyDict> pageDict(IPage page, String dictType);

    SyDict selectDict(String dictContent);
}
