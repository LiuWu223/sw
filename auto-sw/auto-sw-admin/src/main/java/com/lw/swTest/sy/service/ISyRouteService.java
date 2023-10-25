package com.lw.swTest.sy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.entity.SyRoute;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 路由表 服务类
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface ISyRouteService extends IService<SyRoute> {
    /**
     * 通过路由Label查找路由信息
     * @param routeLabel 路由Label
     * @return 返回
     */
    List<SyRoute> selectRouteLabel(String routeLabel);

    /**
     * 分页查询路由信息
     * @param page 分页信息
     * @param routeLabel 路由标题
     * @return 返回分页数据
     */
    IPage<SyRoute> pageRoute(IPage page, String routeLabel);
}
