package com.supernb.stock;

import com.supernb.stock.pojo.vo.TaskThreadPoolInfo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@MapperScan("com.supernb.stock.mapper")
@SpringBootApplication
@EnableConfigurationProperties({TaskThreadPoolInfo.class})
public class JobApp {
    public static void main(String[] args) {
        SpringApplication.run(JobApp.class, args);
    }
}
