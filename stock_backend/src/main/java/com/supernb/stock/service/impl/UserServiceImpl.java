package com.supernb.stock.service.impl;

import com.supernb.stock.domain.vo.req.*;
import com.supernb.stock.domain.vo.resp.*;
import com.supernb.stock.mapper.SysUserMapper;
import com.supernb.stock.pojo.entity.SysUser;
import com.supernb.stock.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public SysUser getUserByUserName(String userName) {
        SysUser user = sysUserMapper.findByUserName(userName);
        return user;
    }

    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        // 判断参数是否异常
        if (vo == null || StringUtils.isBlank(vo.getUsername())
                || StringUtils.isBlank(vo.getPassword())
                || StringUtils.isBlank(vo.getCode()) ) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        // 根据用户名查询用户密码的密文
        SysUser dbUser = sysUserMapper.findByUserName(vo.getUsername());
        if (dbUser == null) {
            return R.error(ResponseCode.ACCOUNT_NOT_EXISTS);
        }
        // 将输入的密码与查询到的密文进行对比
        if (!passwordEncoder.matches(vo.getPassword(),dbUser.getPassword())) {
            return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }

        // 相应
        LoginRespVo loginRespVo = new LoginRespVo();
        BeanUtils.copyProperties(dbUser,loginRespVo);
        return R.ok(loginRespVo);
    }
}
