package com.lw.swTest.sy.controller;

import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.swTest.core.enums.ResponseCodeEnum;
import com.lw.swTest.core.result.ResponseResult;
import com.lw.swTest.core.statics.GlobalStaticVariable;
import com.lw.swTest.sy.OV.SyUsrRoleOV;
import com.lw.swTest.sy.entity.*;
import com.lw.swTest.sy.service.*;
import com.lw.swTest.ts.entity.TsProjectTask;
import com.lw.swTest.ts.entity.TsTaskDetails;
import com.lw.swTest.ts.entity.TsUserProject;
import com.lw.swTest.ts.service.ITsProjectTaskService;
import com.lw.swTest.ts.service.ITsTaskDetailsService;
import com.lw.swTest.ts.service.ITsUserProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.lw.swTest.core.utils.MD5Util.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lw
 * @since 2023-09-17
 */
@RestController
@RequestMapping("/sy/syUser")
@Api(tags = "sy用户管理",value = "sy用户管理")
public class SyUserController {

    private final ISyUserService syUserService;
    private final ISyRoleService syRoleService;
    private final ISyUserRoleService syUserRoleService;
    private final ISyRoleRouteService syRoleRouteService;
    private final ISyDictService sysDictService;
    private final ITsUserProjectService tsUserProjectService;
    private final ITsProjectTaskService tsProjectTaskService;
    private final ITsTaskDetailsService taskDetailsService;

    public SyUserController(ISyUserService syUserService, ISyRoleService syRoleService, ISyUserRoleService syUserRoleService, ISyRoleRouteService syRoleRouteService, ISyDictService sysDictService, ITsUserProjectService tsUserProjectService, ITsProjectTaskService tsProjectTaskService, ITsTaskDetailsService taskDetailsService) {
        this.syUserService = syUserService;
        this.syRoleService = syRoleService;
        this.syUserRoleService = syUserRoleService;
        this.syRoleRouteService = syRoleRouteService;
        this.sysDictService = sysDictService;
        this.tsUserProjectService = tsUserProjectService;
        this.tsProjectTaskService = tsProjectTaskService;
        this.taskDetailsService = taskDetailsService;
    }

    /**
     * 处理路由的上下级
     * @param roleId 角色
     * @return 处理完成的路由
     */
    public List<SyRoleRoute> roleRouteO(Long roleId) {

        // 根据角色ID查询角色路由
        List<SyRoleRoute> roleRouteO = syRoleRouteService.selectRoleIdByRoute(roleId);

        // 遍历跟目录添加子目录
        for (SyRoleRoute roleRoute : roleRouteO) {
            List<Object> menuList = new ArrayList<>();
            for (SyRoleRoute roleRoute2: roleRouteO) {
                 // 判断第二次遍历中的父ID是否等于第一次遍历的id
                if (Objects.equals(roleRoute2.getRouteHigher(), String.valueOf(roleRoute.getRouteId()))) {
                    menuList.add(roleRoute2);
                }
            }
            roleRoute.setChildren(menuList);
        }

        // 确认根目录
        roleRouteO.removeIf(roleRole -> !Objects.equals(roleRole.getRouteHigher(), "0"));
        return roleRouteO;
    }
    @ApiOperation(value = "刷新IP", notes = "刷新IP", httpMethod = "GET")
    @GetMapping("/logsGet/{userAccount}/{userPassword}/{userIp}/{userPath}")
    public ResponseResult pageUser (@PathVariable("userAccount") String userAccount,@PathVariable("userPassword") String userPassword,@PathVariable("userIp") String userIp,@PathVariable("userPath") String userPath) {
        // 利用盐判断是否登录成功
        List<SyUser> users = syUserService.selectUserName(userAccount);
        if( users.size() == 0){
            // 查询失败，没有该账号
            return ResponseResult.error(ResponseCodeEnum.LOGIN_FAIL);
        }
        // 获取数据库中保存的加密密码
        String newPassword = users.get(0).getUserPassword();
        // 判断传入的密码+数据库中保存的盐 是否 等于 数据库中已加密的密码
        boolean isTrue = getSaltverifyMD5(userPassword,newPassword,users.get(0).getSalt());
        // 判断是否通过
        if (!isTrue) {
            return ResponseResult.error(ResponseCodeEnum.LOGIN_FAIL);
        }
        Long userId = users.get(0).getId();
        syUserService.upUserIp(userId, userIp, userPath);
        return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
    }
    /**
     * 登录接口
     * @param syUser 表单对象格式，包含userAccount:用户账号，userPassword:用户密码
     * @return token与用户信息
     */
    @ApiOperation(value = "登录", notes = "登录获取token", httpMethod = "POST")
    @PostMapping(value = "/login")
    public ResponseResult login (@RequestBody SyUser syUser) {
        // 利用盐判断是否登录成功
        List<SyUser> users = syUserService.selectUserName(syUser.getUserAccount());
        if( users.size() == 0){
            // 查询失败，没有该账号
            return ResponseResult.error(ResponseCodeEnum.LOGIN_FAIL);
        }
        // 获取数据库中保存的加密密码
        String newPassword = users.get(0).getUserPassword();
        // 判断传入的密码+数据库中保存的盐 是否 等于 数据库中已加密的密码
        boolean isTrue = getSaltverifyMD5(syUser.getUserPassword(),newPassword,users.get(0).getSalt());
        // 判断是否通过
        if (!isTrue) {
            return ResponseResult.error(ResponseCodeEnum.LOGIN_FAIL);
        }

        // 登录用户ID
        Long userId = users.get(0).getId();
        Long roleId;

        // 用户信息、当前签发时间信息Map
        Map<String, Object> userMap = new HashMap<>();
        // 用户信息
        userMap.put(GlobalStaticVariable.QUESTION_USER, users.get(0));
        // 当前时间
        userMap.put(GlobalStaticVariable.CURRENT_TIME, System.currentTimeMillis());

        // 根据用户ID获取角色ID
        SyUserRole userRoleO = syUserRoleService.selectUserIdByRoleId(userId);
        roleId = userRoleO.getRoleId();
        if (roleId == null) {return ResponseResult.error(ResponseCodeEnum.CTRL_FAIL);}
        // 将用户角色名称set进去
        users.get(0).setRoleName(userRoleO.getRoleName());
        users.get(0).setRoleId(userRoleO.getRoleId());

        // 处理树形菜单
        List<SyRoleRoute> roleRoute = roleRouteO(roleId);
        userMap.put(GlobalStaticVariable.ROLE_ROUTE,roleRoute);

        String token = JWTUtil.createToken(userMap, GlobalStaticVariable.JWT_SECRET.getBytes());
        // 生成的token用map储存
        Map<String, Object> map1 = new HashMap<>();
        map1.put(GlobalStaticVariable.AUTHORIZATION, token);

        return ResponseResult.ok(ResponseCodeEnum.LOGIN_SUCCESS, map1);
    }

    /**
     * 分页查询用户信息
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @param userName 用户名称
     * @param userAccount 用户账号
     * @return 返回分页数据
     */
    @ApiOperation(value = "分页查询用户信息", notes = "分页查询用户信息", httpMethod = "GET")
    @GetMapping("/pageUser")
    public ResponseResult pageUser (@RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("pageSize") Integer pageSize,
                                    String userName,
                                    String userAccount,
                                    String roleName) {
        if (Objects.equals(roleName, "全部")) roleName = "";
        IPage<SyUser> iPage = new Page<>(currentPage, pageSize);
        IPage<SyUser> page = syUserService.pageUser(iPage, userName, userAccount, roleName);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, page);
    }

    /**
     * 多选删除用户信息
     * @param userIds 用户信息列表
     * @return 返
     */
    @DeleteMapping("/removeUserIds")
    @ApiOperation(value = "多选删除用户信息", notes = "多选删除用户信息", httpMethod = "DELETE")
    public ResponseResult removeUserIds (Long[] userIds) {
        List<Long> listUserIds = Arrays.asList(userIds);
        boolean removes = syUserService.removeBatchByIds(listUserIds);
        for (Long userId : listUserIds) {
            syUserRoleService.removeUserRoleByUserId(userId);
        }
        // 删除身份信息
        if (!removes) {
            return ResponseResult.error(ResponseCodeEnum.REMOVE_FAIL);
        }
        return ResponseResult.ok(ResponseCodeEnum.REMOVE_SUCCESS);
    }


    @ApiOperation(value = "ID查询用户信息", notes = "ID查询用户信息", httpMethod = "GET")
    @GetMapping("/selectUserById/{userId}")
    public ResponseResult selectUserById (@PathVariable("userId") Long userId) {
        SyUser user = syUserService.selectUserById(userId);
        return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS, user);
    }

    /**
     * 修改用户信息，用户身份
     * @param syUsrRoleOV 用户表、用户身份表
     * @return 结果
     */
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/updateUser")
    public ResponseResult updateUser (@RequestBody SyUsrRoleOV syUsrRoleOV) {
        // == 处理密码
        // 随机生成盐
        String salt = getSalt();
        // 新密码为  盐 + 传来的密码，这个密码将保存在数据库
        String newPassword = getSaltMD5(syUsrRoleOV.getSyUser().getUserPassword(), salt);
        syUsrRoleOV.getSyUser().setUserPassword(newPassword);
        syUsrRoleOV.getSyUser().setSalt(salt);

        // 判断新增或修改
        if (syUsrRoleOV.getSyUser().getId() == null) {
            // 判断是否重复
            List<SyUser> users = syUserService.selectUserName(syUsrRoleOV.getSyUser().getUserAccount());
            if( users.size() > 0){
                return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);
            }
            // 新增
            SyDict dictC = sysDictService.selectDict(String.valueOf(syUsrRoleOV.getSyUser().getDictId()));
            syUsrRoleOV.getSyUser().setDictId(String.valueOf(dictC.getId()));
            boolean saveUser = syUserService.saveOrUpdate(syUsrRoleOV.getSyUser());
            if (!saveUser) {
                return ResponseResult.ok(ResponseCodeEnum.SAVE_FAIL);
            }

            List<SyUser> users2 = syUserService.selectUserName(syUsrRoleOV.getSyUser().getUserAccount());
            // 写入用户ID
            syUsrRoleOV.getSyUserRole().setUserId(users2.get(0).getId());

            SyRole roles = syRoleService.selectRoleByName(syUsrRoleOV.getSyUserRole().getRoleName());
            syUsrRoleOV.getSyUserRole().setRoleId(roles.getId());

            boolean saveRole = syUserRoleService.saveOrUpdate(syUsrRoleOV.getSyUserRole());

            if (!saveRole) {
                return ResponseResult.ok(ResponseCodeEnum.SAVE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.SAVE_SUCCESS);
        } else {
            // 判断是否重复
            List<SyUser> users = syUserService.selectUserName(syUsrRoleOV.getSyUser().getUserAccount());
            if( users.size() > 1){
                return ResponseResult.error(ResponseCodeEnum.SAVE_EXIST);
            }
            // 新增
            SyDict dictC = sysDictService.selectDict(String.valueOf(syUsrRoleOV.getSyUser().getDictId()));
            syUsrRoleOV.getSyUser().setDictId(String.valueOf(dictC.getId()));
            boolean saveUser = syUserService.saveOrUpdate(syUsrRoleOV.getSyUser());
            if (!saveUser) {
                return ResponseResult.ok(ResponseCodeEnum.UPDATE_FAIL);
            }
            // 写入用户ID
            syUsrRoleOV.getSyUserRole().setUserId(syUsrRoleOV.getSyUser().getId());

            SyRole roles = syRoleService.selectRoleByName(syUsrRoleOV.getSyUserRole().getRoleName());
            syUsrRoleOV.getSyUserRole().setRoleId(roles.getId());

            boolean saveRole = syUserRoleService.saveOrUpdate(syUsrRoleOV.getSyUserRole());

            if (!saveRole) {
                return ResponseResult.ok(ResponseCodeEnum.UPDATE_FAIL);
            }
            return ResponseResult.ok(ResponseCodeEnum.UPDATE_SUCCESS);
        }
    }


    @ApiOperation(value = "刷新IP", notes = "刷新IP", httpMethod = "GET")
    @GetMapping("/selectByUserIp/{userIp}")
    public ResponseResult selectByUserIp (@PathVariable("userIp") String userIp) {
        try {
            // 返回所有任务
            // 通过IP查询用户信息
//            SyUser byIpUser = syUserService.selectByUserIp(userIp);
//            // 通过用户ID，查找用户项目
//            List<TsUserProject> userProjectList =  tsUserProjectService.selectProjectByUserId(String.valueOf(byIpUser.getId()));
//            ArrayList<TsTaskDetails> userTaskArray = new ArrayList<>();
//            for (TsUserProject userProject: userProjectList) {
//                List<TsProjectTask> dictList = tsProjectTaskService.selectTaskByProjectId(String.valueOf(userProject.getProjectId()));
//                for (TsProjectTask discts : dictList) {
//                    List<TsTaskDetails> uts =  taskDetailsService.selectTaskCaseByTaskId(discts.getTaskId());
//                    for (TsTaskDetails ttds : uts){
//                        ttds.setDevice(discts.getDevices());
//                    }
//                    userTaskArray.addAll(uts);
//                }
//            }
            // 返回最新的一个任务
            // 通过IP查询用户信息
            SyUser byIpUser = syUserService.selectByUserIp(userIp);
            // 通过用户ID，查找用户项目
            List<TsUserProject> userProjectList =  tsUserProjectService.selectProjectByUserId(String.valueOf(byIpUser.getId()));
            List<TsProjectTask> dictList = tsProjectTaskService.selectTaskByProjectIdReNewTime(String.valueOf(userProjectList.get(0).getProjectId()));
            return ResponseResult.ok(ResponseCodeEnum.SELECT_SUCCESS,dictList.get(0));
        } catch (Exception e) {
            return ResponseResult.error(ResponseCodeEnum.SELECT_FAIL,"空！！无任务");
        }
    }
}
