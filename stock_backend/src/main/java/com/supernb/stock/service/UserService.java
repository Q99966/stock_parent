package com.supernb.stock.service;

import com.supernb.stock.domain.vo.req.*;
import com.supernb.stock.domain.vo.resp.*;
import com.supernb.stock.pojo.entity.SysUser;

public interface UserService {
    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    SysUser getUserByUserName(String userName);

    /**
     * 登录
     */
    R<LoginRespVo> login(LoginReqVo vo);
}
