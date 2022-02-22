package com.yjy.moxibustion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.yjy.moxibustion.dao")
@SpringBootApplication
public class MoxibustionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoxibustionApplication.class, args);
    }

}
