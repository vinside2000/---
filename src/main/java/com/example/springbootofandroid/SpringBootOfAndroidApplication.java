package com.example.springbootofandroid;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springbootofandroid.mapper")
public class SpringBootOfAndroidApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOfAndroidApplication.class, args);
    }

}
