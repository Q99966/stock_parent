package com.supernb.stock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableFeignClients(basePackages = "com.example.client")
@MapperScan("com.supernb.stock.mapper")
@SpringBootApplication
public class BackendApp {
    public static void main(String[] args) {

        SpringApplication.run(BackendApp.class, args);
    }
}
