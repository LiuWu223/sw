package com.lw.swTest.py.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.py.entity.PyFlask;
import com.lw.swTest.py.mapper.PyFlaskMapper;
import com.lw.swTest.py.service.IPyFlaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * PythonFlask接口 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-19
 */
@Service
public class PyFlaskServiceImpl extends ServiceImpl<PyFlaskMapper, PyFlask> implements IPyFlaskService {
    /**
     * 通过flask名称查找flask信息
     * @param flaskName flaskName
     * @return 返回
     */
    @Override
    public List<PyFlask> selectFlaskName(String flaskName) {
        return baseMapper.selectFlaskName(flaskName);
    }
    /**
     * 分页查询Flask信息
     * @param page 分页参数
     * @param flaskName flaskName
     * @return f
     */
    @Override
    public IPage<PyFlask> pageFlask(IPage page, String flaskName) {
        return baseMapper.pageFlask(page,flaskName);
    }
}
