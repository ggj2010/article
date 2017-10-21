package com.ggj.article.module.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ExecutorConfiguration {
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(Integer.parseInt("5"));
        threadPoolTaskExecutor.setKeepAliveSeconds(Integer.parseInt("30000"));
        threadPoolTaskExecutor.setMaxPoolSize(Integer.parseInt("20"));
        threadPoolTaskExecutor.setQueueCapacity(Integer.parseInt("100"));
        return threadPoolTaskExecutor;
    }
}
