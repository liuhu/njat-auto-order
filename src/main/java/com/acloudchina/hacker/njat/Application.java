package com.acloudchina.hacker.njat;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:01
 **/
@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }
}
