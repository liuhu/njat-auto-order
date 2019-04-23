package com.acloudchina.hacker.njat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 11:32
 **/
@Configuration
@ConfigurationProperties(prefix = "dynamic.config")
@Data
public class DynamicConfig {

    private String venuePriority;

    /**
     * 获取场馆优先级
     * @return
     */
    public List<Integer> getVenuePriority() {
        return Arrays.asList(venuePriority.split(",")).stream().map(Integer::valueOf).collect(Collectors.toList());
    }
}
