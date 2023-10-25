package com.lw.swTest.sy.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lw.swTest.sy.entity.SyRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface SyDictMapper extends BaseMapper<SyDict> {

    /**
     * 通过字典类型查找字典信息
     * @param dictType 字典类型
     * @return 返回
     */
    List<SyDict> selectDictType(@Param("dictType") String dictType);

    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param dictType 字典类型
     * @return f
     */
    IPage<SyDict> pageDict(IPage page,@Param("dictType") String dictType);

    SyDict selectDict (@Param("dictContent") String dictContent);
}
