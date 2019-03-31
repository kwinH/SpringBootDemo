package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("com.example.mapper.dao")//注意MapperScan要导tk的包，不然会报NoSuchMethodException
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
