package com.lw.swTest.sy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.service.ISyRoleService;
import com.lw.swTest.system.entity.SystemRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/sy/syRole")
@Api(tags = "sy角色管理",value = "sy角色管理")
public class SyRoleController {
    private final ISyRoleService syRoleService;

    public SyRoleController(ISyRoleService syRoleService) {
        this.syRoleService = syRoleService;
    }

    /**
     * 分页查询角色信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param roleName 角色名称
     * @param roleCode 角色Code
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息", httpMethod = "GET")
    @GetMapping("/pageRole")
    public ResponseResult pageRole (@RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("pageSize") Integer pageSize,
                                    String roleName,
                                    String roleCode) {
        IPage<SyRole> iPage = new Page<>(currentPage, pageSize);
        IPage<SyRole> page = syRoleService.pageRole(iPage, roleName, roleCode);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改角色信息
     * @param syRole 角色信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改角色信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/roleSaveOrUp")
    public ResponseResult roleSaveOrUp (@RequestBody SyRole syRole) {
        // 用户ID，用来判断是修改还是新增
        Long id = syRole.getId();
        if (id == null || id == 0){
            // 新增，需要判断用户账号是否重复
            List<SyRole> users = syRoleService.selectRoleName(syRole.getRoleName());
            if( users.size() != 0){return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);}

            boolean save = syRoleService.saveOrUpdate(syRole);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = syRoleService.saveOrUpdate(syRole);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除角色信息
     * @param roleIds 角色信息列表
     * @return 返
     */
    @DeleteMapping("/removeRoleIds")
    @ApiOperation(value = "多选删除角色信息", notes = "多选删除角色信息", httpMethod = "DELETE")
    public ResponseResult removeRoleIds (Long[] roleIds) {
        if (roleIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(roleIds);
        boolean removes = syRoleService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }

    /**
     * 查询搜索角色
     * @return 所有角色
     */
    @ApiOperation(value = "查询所有角色信息", notes = "查询所有角色信息", httpMethod = "GET")
    @GetMapping("/selectRole")
    public ResponseResult selectRole () {
        List<SyRole> systemRoles = syRoleService.list();
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, systemRoles);
    }
}
