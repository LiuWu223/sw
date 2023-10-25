package com.lw.swTest.py.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.py.entity.PyFlask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lw.swTest.tol.entity.TolGit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * PythonFlask接口 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
public interface PyFlaskMapper extends BaseMapper<PyFlask> {
    /**
     * 通过flask名称查找flask信息
     * @param flaskName flaskName
     * @return 返回
     */
    List<PyFlask> selectFlaskName(@Param("flaskName") String flaskName);

    /**
     * 分页查询字典信息
     * @param page 分页参数
     * @param flaskName flaskName
     * @return f
     */
    IPage<PyFlask> pageFlask(IPage page, @Param("flaskName") String flaskName);
}
