package com.yue.sso.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by 千里明月 on 2018/7/18.
 */
@SpringBootApplication
@ComponentScan({"com.yue.sso.client"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
