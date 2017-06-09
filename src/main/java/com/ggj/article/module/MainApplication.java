package com.ggj.article.module;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
//搜索servlert
@ServletComponentScan
@SpringBootApplication
//扫描mybatis接口
@MapperScan("com.ggj")
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class);
    }
}
