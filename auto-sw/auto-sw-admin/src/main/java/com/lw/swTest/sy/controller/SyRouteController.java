package com.lw.swTest.sy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.sy.entity.SyRoute;
import com.lw.swTest.sy.service.ISyRouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 路由表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/sy/syRoute")
@Api(tags = "sy路由管理",value = "sy路由管理")
public class SyRouteController {
    private final ISyRouteService iSyRouteService;

    public SyRouteController(ISyRouteService iSyRouteService) {
        this.iSyRouteService = iSyRouteService;
    }


    /**
     * 分页查询路由信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param routeLabel 路由标题
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询路由信息", notes = "分页查询路由信息", httpMethod = "GET")
    @GetMapping("/pageRoute")
    public ResponseResult pageUser (@RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("pageSize") Integer pageSize,
                                    String routeLabel) {
        IPage<SyRoute> iPage = new Page<>(currentPage, pageSize);
        IPage<SyRoute> page = iSyRouteService.pageRoute(iPage, routeLabel);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改路由信息
     * @param syRoute 路由信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改路由信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/syRouteSaveOrUp")
    public ResponseResult syRouteSaveOrUp (@RequestBody SyRoute syRoute) {
        // ID，用来判断是修改还是新增
        Long id = syRoute.getId();
        if (id == null || id == 0){
            // 新增，需要判断是否重复
            List<SyRoute> authority = iSyRouteService.selectRouteLabel(syRoute.getRouteLabel());
            if( authority.size() != 0){return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);}

            boolean save = iSyRouteService.saveOrUpdate(syRoute);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = iSyRouteService.saveOrUpdate(syRoute);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除路由信息
     * @param routeIds 路由信息列表
     * @return 返
     */
    @DeleteMapping("/removeRouteIds")
    @ApiOperation(value = "多选删除路由信息", notes = "多选删除路由信息", httpMethod = "DELETE")
    public ResponseResult removeRouteIds (Long[] routeIds) {
        if (routeIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoutes = Arrays.asList(routeIds);
        boolean removes = iSyRouteService.removeBatchByIds(listRoutes);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
