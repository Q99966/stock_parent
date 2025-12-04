package com.supernb.stock.controller;


import com.supernb.stock.domain.vo.req.LoginReqVo;
import com.supernb.stock.domain.vo.resp.LoginRespVo;
import com.supernb.stock.domain.vo.resp.R;
import com.supernb.stock.pojo.entity.SysUser;
import com.supernb.stock.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
/*
 * 定义web层接口资源bean
 */

@RestController
@RequestMapping("/api")
@Api(value = "用户认证相关接口定义",tags = "用户功能-用户登录功能")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户功能
     * @param userName
     * @return
     */
    @GetMapping("/user/{userName}")
    @ApiOperation(value = "根据用户名查询用户信息",notes = "用户信息查询",response = SysUser.class)
    @ApiImplicitParam(paramType = "path",name = "userName",value = "用户名",required = true)
    public SysUser getUserByUserName(@PathVariable("userName") String userName) {
        return userService.getUserByUserName(userName);
    }

    /**
     * 登录功能
     * @param vo
     * @return
     */

    @PostMapping("/login")
    @ApiOperation(value = "用户登录功能",notes = "用户登录",response = R.class)
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo) {
        return userService.login(vo);
    }

    /**
     * 生成登录校验码的访问接口
     * @return
     */
    @GetMapping("/captcha")
    @ApiOperation(value = "验证码生成功能",response = R.class)
    public R<Map> getCaptchaCode(){
        return userService.getCaptchaCode();
    }
}
