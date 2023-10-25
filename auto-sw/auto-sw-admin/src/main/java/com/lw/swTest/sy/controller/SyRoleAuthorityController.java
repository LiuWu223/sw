package com.lw.swTest.sy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.sy.entity.SyRoleAuthority;
import com.lw.swTest.sy.entity.SyRoleRoute;
import com.lw.swTest.sy.service.ISyRoleAuthorityService;
import com.lw.swTest.sy.service.ISyRoleRouteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色权限表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/sy/syRoleAuthority")
@Api(tags = "sy角色权限管理",value = "sy角色权限管理")
public class SyRoleAuthorityController {
    private final ISyRoleAuthorityService syRoleAuthorityService;

    public SyRoleAuthorityController(ISyRoleAuthorityService syRoleAuthorityService) {
        this.syRoleAuthorityService = syRoleAuthorityService;
    }

    /**
     * 分页查询用户信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param authorityName 权限名称
     * @param roleName 角色名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询角色权限信息", notes = "分页查询角色权限信息", httpMethod = "GET")
    @GetMapping("/pageRoleAuthority")
    public ResponseResult pageRoleAuthority (@RequestParam("currentPage") Integer currentPage,
                                         @RequestParam("pageSize") Integer pageSize,
                                         String authorityName,
                                         String roleName) {
        IPage<SyRoleAuthority> iPage = new Page<>(currentPage, pageSize);
        IPage<SyRoleAuthority> page = syRoleAuthorityService.pageRoleAuthority(iPage, authorityName, roleName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改用户权限信息
     * @param syRoleAuthority 角色权限信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改角色权限信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/roleAuthoritySaveOrUp")
    public ResponseResult roleAuthoritySaveOrUp (@RequestBody SyRoleAuthority syRoleAuthority) {
        // 用户ID，用来判断是修改还是新增
        Long id = syRoleAuthority.getId();
        if (id == null || id == 0){
            boolean save = syRoleAuthorityService.saveOrUpdate(syRoleAuthority);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = syRoleAuthorityService.saveOrUpdate(syRoleAuthority);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除角色权限信息
     * @param roleAuthorityIds 角色权限信息列表
     * @return 返
     */
    @DeleteMapping("/removeRoleAuthorityIds")
    @ApiOperation(value = "多选删除角色权限信息", notes = "多选删除角色权限信息", httpMethod = "DELETE")
    public ResponseResult removeRoleAuthorityIds (Long[] roleAuthorityIds) {
        if (roleAuthorityIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(roleAuthorityIds);
        boolean removes = syRoleAuthorityService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
