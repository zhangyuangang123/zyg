package com.zyg.getway;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created with IDEA
 * @author :zyg
 * @date :2021/01/27
 */
@SpringBootApplication
@EnableEurekaServer
@EnableApolloConfig
public class GetwayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GetwayApplication.class,args);
    }
}
