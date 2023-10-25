package com.lw.swTest.sy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.sy.entity.SyRoleRoute;
import com.lw.swTest.sy.entity.SyUserRole;
import com.lw.swTest.sy.service.ISyRoleRouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色路由表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/sy/syRoleRoute")
@Api(tags = "sy角色路由管理",value = "sy角色路由管理")
public class SyRoleRouteController {
    private final ISyRoleRouteService syRoleRouteService;


    public SyRoleRouteController(ISyRoleRouteService syRoleRouteService) {
        this.syRoleRouteService = syRoleRouteService;
    }

    /**
     * 分页查询用户信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param routeLabel 路由Label
     * @param roleName 角色名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询角色路由信息", notes = "分页查询角色路由信息", httpMethod = "GET")
    @GetMapping("/pageRoleRoute")
    public ResponseResult pageRoleRoute (@RequestParam("currentPage") Integer currentPage,
                                        @RequestParam("pageSize") Integer pageSize,
                                        String routeLabel,
                                        String roleName) {
        IPage<SyRoleRoute> iPage = new Page<>(currentPage, pageSize);
        IPage<SyRoleRoute> page = syRoleRouteService.pageRoleRoute(iPage, routeLabel, roleName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改用户角色信息
     * @param syRoleRoute 角色路由信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改角色路由信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/roleRouteSaveOrUp")
    public ResponseResult roleRouteSaveOrUp (@RequestBody SyRoleRoute syRoleRoute) {
        // 用户ID，用来判断是修改还是新增
        Long id = syRoleRoute.getId();
        if (id == null || id == 0){
            boolean save = syRoleRouteService.saveOrUpdate(syRoleRoute);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = syRoleRouteService.saveOrUpdate(syRoleRoute);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除角色路由信息
     * @param roleRouteIds 角色路由信息列表
     * @return 返
     */
    @DeleteMapping("/removeRoleRouteIds")
    @ApiOperation(value = "多选删除角色路由信息", notes = "多选删除角色路由信息", httpMethod = "DELETE")
    public ResponseResult removeRoleRouteIds (Long[] roleRouteIds) {
        if (roleRouteIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(roleRouteIds);
        boolean removes = syRoleRouteService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
