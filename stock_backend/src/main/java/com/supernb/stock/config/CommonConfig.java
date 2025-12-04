package com.supernb.stock.config;

import com.supernb.stock.pojo.vo.StockInfoConfig;
import com.supernb.stock.utils.IdWorker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 定义公共配置类
 */
@Configuration
@EnableConfigurationProperties({StockInfoConfig.class}) // 加载相关配置类
public class CommonConfig {
    /**
     * 密码加密器
     * BCryptPasswordEncoder方法采用SHA-256对密码进行加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 基于雪花算法的分布式SessionID生成器
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        /**
         * 假定一号机器 ，三号机房
         */
        return new IdWorker(1L,3L);
    }

}