package com.lw.swTest.sy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.entity.SyRoute;
import com.lw.swTest.sy.mapper.SyRouteMapper;
import com.lw.swTest.sy.service.ISyRouteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 路由表 服务实现类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@Service
public class SyRouteServiceImpl extends ServiceImpl<SyRouteMapper, SyRoute> implements ISyRouteService {
    /**
     * 通过路由Label查找路由信息
     * @param routeLabel 路由Label
     * @return 返回
     */
    @Override
    public List<SyRoute> selectRouteLabel(String routeLabel) {
        return baseMapper.selectRouteLabel(routeLabel);
    }

    /**
     * 分页查询路由信息
     * @param page 分页信息
     * @param routeLabel 路由标题
     * @return 返回分页数据
     */
    @Override
    public IPage<SyRoute> pageRoute(IPage page, String routeLabel) {
        return baseMapper.pageRoute(page,routeLabel);
    }
}
