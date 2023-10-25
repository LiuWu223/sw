package com.lw.swTest.py.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.py.entity.PyFlask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lw.swTest.tol.entity.TolGit;

import java.util.List;

/**
 * <p>
 * PythonFlask接口 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
public interface IPyFlaskService extends IService<PyFlask> {
    /**
     * 通过flask名称查找flask信息
     * @param flaskName flaskName
     * @return 返回
     */
    List<PyFlask> selectFlaskName(String flaskName);
    /**
     * 分页查询Flask信息
     * @param page 分页参数
     * @param flaskName flaskName
     * @return f
     */
    IPage<PyFlask> pageFlask(IPage page, String flaskName);
}
