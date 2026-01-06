package com.supernb.stock.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.supernb.stock.constant.StockConstant;
import com.supernb.stock.domain.vo.req.*;
import com.supernb.stock.domain.vo.resp.*;
import com.supernb.stock.mapper.SysUserMapper;
import com.supernb.stock.pojo.entity.SysUser;
import com.supernb.stock.service.UserService;
import com.supernb.stock.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    @Override
    public SysUser getUserByUserName(String userName) {
        return sysUserMapper.findByUserName(userName);
    }

    /**
     * 登录功能
     * @param vo
     * @return
     */
    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
        // 判断参数是否异常
        if (vo == null || StringUtils.isBlank(vo.getUsername())
                || StringUtils.isBlank(vo.getPassword())
                || StringUtils.isBlank(vo.getCode()) ) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        // 判断校验码是否为空
        if(StringUtils.isBlank(vo.getSessionId())
                || StringUtils.isBlank(vo.getCode())){
            return R.error(ResponseCode.CHECK_CODE_ERROR);
        }
        // 验证校验码
        String redisCode = (String) redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX + vo.getSessionId());
        if(StringUtils.isBlank(redisCode)){
            // 验证码过期
            return R.error(ResponseCode.CHECK_CODE_TIMEOUT);
        }
        if(!redisCode.equalsIgnoreCase(vo.getCode())){
            // 验证码错误
            return R.error(ResponseCode.CHECK_CODE_ERROR);
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

    /**
     * 生存图片验证码功能
     * @return
     */
    @Override
    public R<Map> getCaptchaCode() {
        //1. 生成图片验证码
        //参数分别是宽、高、验证码长度、干扰线数量
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        // 设置背景颜色
        captcha.setBackground(Color.gray);
        //获取校验码
        String checkCode = captcha.getCode();
        // 获取经过Base64编码后的图片
        String base64Img = captcha.getImageBase64();
        //2. 生成SessionID,并转换为String，避免前端精度丢失
        String sessionId = String.valueOf(idWorker.nextId());
        log.info("生成校验码:{}，生成的SessionID:{}",checkCode,sessionId);
        //3.将sessionId作为key，验证码作为value，存入Redis，并设置过期时间为1分钟
        // 增加业务前缀，方便运维时区分接口调用
        redisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX + sessionId,checkCode,5, TimeUnit.MINUTES);
        //4.组装数据
        Map<String,String> data = new HashMap<>();
        data.put("imageData",base64Img);
        data.put("sessionId",sessionId);
        //5.相应数据
        return R.ok(data);
    }
}
