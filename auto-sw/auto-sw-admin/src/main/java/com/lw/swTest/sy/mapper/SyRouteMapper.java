package com.lw.swTest.sy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lw.swTest.sy.entity.SyRoute;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 路由表 Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
public interface SyRouteMapper extends BaseMapper<SyRoute> {
    /**
     * 通过路由Label查找路由信息
     * @param routeLabel 路由Label
     * @return 返回
     */
    List<SyRoute> selectRouteLabel(@Param("routeLabel") String routeLabel);

    /**
     * 分页查询路由信息
     * @param page 分页信息
     * @param routeLabel 路由标题
     * @return 返回分页数据
     */
    IPage<SyRoute> pageRoute(IPage page, @Param("routeLabel") String routeLabel);
}
