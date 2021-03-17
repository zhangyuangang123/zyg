package com.zyg.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "zyg-properties",name="show-sql", havingValue="true")
@Slf4j
public class MyBatisConfig {

    /**
     * 配置 sql打印拦截器
     *
     * @return SqlStatementInterceptor
     */
    @Bean
    SqlStatementInterceptor sqlStatementInterceptor() {
        log.info("开启sql日志打印：sqlStatementInterceptor");
        return new SqlStatementInterceptor();
    }
}
