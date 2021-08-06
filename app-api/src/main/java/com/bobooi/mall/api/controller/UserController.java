package com.bobooi.mall.api.controller;

import com.bobooi.mall.api.module.vo.UserDetailVO;
import com.bobooi.mall.api.module.vo.UserVO;
import com.bobooi.mall.data.entity.CsmAddr;
import com.bobooi.mall.data.entity.CsmLogin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bobooi.mall.common.exception.ApplicationException;
import com.bobooi.mall.common.response.ApplicationResponse;
import com.bobooi.mall.common.response.SystemCodeEnum;
import com.bobooi.mall.common.utils.misc.Constant;
import com.bobooi.mall.common.utils.misc.JwtUtil;
import com.bobooi.mall.data.config.redis.RedisUtil;
import com.bobooi.mall.data.bo.valid.UserEditValidGroup;
import com.bobooi.mall.data.bo.valid.UserLoginValidGroup;
import com.bobooi.mall.data.service.concrete.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author bobo
 * @date 2021/3/31
 */
@CrossOrigin("*")
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    /**
     * 管理员获取地址用户列表
     * @return 用户地址列表
     */
    @ApiOperation("管理员获取用户地址列表")
    @GetMapping("/addr")
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    public ApplicationResponse<List<CsmAddr>> getUserAddr() {
        return ApplicationResponse.succeed(userService.getUserAddrList());
    }

    /**
     * 管理员获取用户列表
     * @return 用户列表
     */
    @ApiOperation("管理员获取用户详细信息列表")
    @GetMapping
    @RequiresPermissions(logical = Logical.AND, value = {"csmLogin:*"})
    public ApplicationResponse<List<UserDetailVO>> getAll() {
        return ApplicationResponse.succeed(userService.getDetailUserList().stream()
                .map(UserDetailVO::fromUserDetailBO)
                .collect(Collectors.toList()));
    }

    /**
     * 获取登录信息
     * @return 登录信息
     */
    @ApiOperation("获取登录信息")
    @GetMapping("/info")
    @RequiresAuthentication
    public ApplicationResponse<UserVO> info() {
        CsmLogin csmLogin = userService.info();
        // 用户是否存在
        if (csmLogin == null) {
            return ApplicationResponse.fail(SystemCodeEnum.NEED_LOGIN,"未登录");
        }
        // 获取当前登录用户
        return ApplicationResponse.succeed("您已经登录了", UserVO.fromUser(csmLogin));
    }

//    /**
//     * 用户注册
//     * @return 注册后信息
//     */
//    @PostMapping("/register")
//    public ApplicationResponse<UserVO> register(@Validated(UserEditValidGroup.class) CsmLogin csmLogin) {
//        return add(csmLogin);
//    }

//    /**
//     * 管理员新增用户
//     * @return 新增后信息
//     */
//    @PostMapping
//    @RequiresPermissions(logical = Logical.AND, value = {"user:add"})
//    public ApplicationResponse<UserVO> add(@Validated(UserEditValidGroup.class) CsmLogin csmLogin) {
//        try {
//            csmLogin = userService.addUser(csmLogin);
//        } catch (Exception exception) {
//            throw new ApplicationException(SystemCodeEnum.ARGUMENT_WRONG, exception.getMessage());
//        }
//        return ApplicationResponse.succeed(UserVO.fromUser(csmLogin));
//    }

    /**
     * 用户登录
     * @return 登录结果
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ApplicationResponse<Void> login(String account, String password, HttpServletResponse httpServletResponse) throws Exception {
        if(!userService.match(account, password)){
            return ApplicationResponse.fail(SystemCodeEnum.NEED_LOGIN, "帐号不存在或密码错误");
        }

        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        RedisUtil.setObject(Constant.REDIS_REFRESH_TOKEN + account, currentTimeMillis,
                Long.parseLong(Constant.REFRESH_TOKEN_EXPIRE_TIME));
        String token = JwtUtil.sign(account, currentTimeMillis);
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        return ApplicationResponse.succeed("登录成功");
    }

    /**
     * 剔除登录状态
     * @return 剔除结果
     */
    @ApiOperation("注销")
    @DeleteMapping("/logout")
    public ApplicationResponse<String> deleteOnline() {
        CsmLogin csmLogin = userService.info();
        if(csmLogin ==null){
            return ApplicationResponse.fail(SystemCodeEnum.ARGUMENT_WRONG, "用户不存在！");
        }
        String key = Constant.REDIS_REFRESH_TOKEN + csmLogin.getLoginName();
        if(RedisUtil.hasKey(key)){
            RedisUtil.deleteKey(key);
        }
        return ApplicationResponse.succeed();
    }
}
