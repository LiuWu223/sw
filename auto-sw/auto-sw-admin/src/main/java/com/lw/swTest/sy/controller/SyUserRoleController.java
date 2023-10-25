package com.lw.swTest.sy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.sy.entity.SyRole;
import com.lw.swTest.sy.entity.SyUserRole;
import com.lw.swTest.sy.service.ISyUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/sy/syUserRole")
@Api(tags = "sy用户角色管理",value = "sy用户角色管理")
public class SyUserRoleController {

    private final ISyUserRoleService syUserRoleService;

    public SyUserRoleController(ISyUserRoleService syUserRoleService) {
        this.syUserRoleService = syUserRoleService;
    }

    /**
     * 分页查询用户信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param userName 用户名称
     * @param roleName 角色名称
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询用户角色信息", notes = "分页查询用户角色信息", httpMethod = "GET")
    @GetMapping("/pageUserRole")
    public ResponseResult pageUserRole (@RequestParam("currentPage") Integer currentPage,
                                        @RequestParam("pageSize") Integer pageSize,
                                        String userName,
                                        String roleName) {
        IPage<SyUserRole> iPage = new Page<>(currentPage, pageSize);
        IPage<SyUserRole> page = syUserRoleService.pageUserRole(iPage, userName, roleName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 新增/修改用户角色信息
     * @param syUserRole 用户角色信息
     * @return 无
     */
    @ApiOperation(value = "新增/修改用户角色信息", notes = "有ID为修改信息，无ID为新增信息", httpMethod = "POST")
    @PostMapping("/userRoleSaveOrUp")
    public ResponseResult userRoleSaveOrUp (@RequestBody SyUserRole syUserRole) {
        // 用户ID，用来判断是修改还是新增
        Long id = syUserRole.getId();
        if (id == null || id == 0){
            boolean save = syUserRoleService.saveOrUpdate(syUserRole);
            if (!save) {
                return ResponseResult.error(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        }
        // 修改
        boolean save = syUserRoleService.saveOrUpdate(syUserRole);
        if (!save) {
            return ResponseResult.error(ResponseCodeEnum.UPDATE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }

    /**
     * 多选删除角色信息
     * @param userRoleIds 角色信息列表
     * @return 返
     */
    @DeleteMapping("/removeUserRoleIds")
    @ApiOperation(value = "多选删除用户角色信息", notes = "多选删除用户角色信息", httpMethod = "DELETE")
    public ResponseResult removeUserRoleIds (Long[] userRoleIds) {
        if (userRoleIds.length < 1){
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        List<Long> listRoleIds = Arrays.asList(userRoleIds);
        boolean removes = syUserRoleService.removeBatchByIds(listRoleIds);
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }
}
