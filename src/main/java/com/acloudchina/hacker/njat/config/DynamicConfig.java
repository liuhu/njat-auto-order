package com.acloudchina.hacker.njat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 11:32
 **/
@Configuration
@ConfigurationProperties(prefix = "dynamic.config")
@Data
public class DynamicConfig {
    private String loginType;
    private String password;
    private String phoneNumber;
}
