package com.acloudchina.hacker.njat.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    /**
     * 场地优先级
     */
    private Map<String, String> venuePriority;

    /**
     * 获取场馆优先级
     * @return
     */
    public List<Integer> getVenuePriority(String venueCode) {
        String venuePriorityStr =  venuePriority.get(venueCode);
        if (StringUtils.isNotBlank(venuePriorityStr)) {
            return Arrays.asList(venuePriorityStr.split(",")).stream().map(Integer::valueOf).collect(Collectors.toList());
        }
        return Arrays.asList(1, 2, 3);
    }
}
