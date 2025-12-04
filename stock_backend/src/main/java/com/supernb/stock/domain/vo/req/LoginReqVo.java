package com.supernb.stock.domain.vo.req;

import lombok.Data;

/**
* 登录请求vo
* */

@Data
public class LoginReqVo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;
    /**
     * 会话ID
     */
    private String sessionId;
}
