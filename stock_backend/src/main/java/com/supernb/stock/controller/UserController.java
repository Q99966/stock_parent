package com.supernb.stock.controller;


import com.supernb.stock.domain.vo.req.LoginReqVo;
import com.supernb.stock.domain.vo.resp.LoginRespVo;
import com.supernb.stock.domain.vo.resp.R;
import com.supernb.stock.pojo.entity.SysUser;
import com.supernb.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/*
 * 定义web层接口资源bean
 */

@RestController
@RequestMapping("/api")

public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户功能
     * @param userName
     * @return
     */
    @GetMapping("/user/{userName}")
    public SysUser getUserByUserName(@PathVariable("userName") String userName) {
        return userService.getUserByUserName(userName);
    }

    /**
     * 登录功能
     * @param vo
     * @return
     */

    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo) {
        return userService.login(vo);
    }
}
