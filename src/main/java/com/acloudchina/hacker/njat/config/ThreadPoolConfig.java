package com.acloudchina.hacker.njat.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 11:32
 **/
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("order-pool-%d").build();
        return new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(100), factory, new ThreadPoolExecutor.AbortPolicy());
    }
}
