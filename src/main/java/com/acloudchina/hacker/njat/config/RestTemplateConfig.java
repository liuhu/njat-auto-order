package com.acloudchina.hacker.njat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 00:12
 **/
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 18080));
//        requestFactory.setProxy(proxy);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }


}
