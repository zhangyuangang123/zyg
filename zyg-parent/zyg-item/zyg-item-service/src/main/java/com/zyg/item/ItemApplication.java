package com.zyg.item;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created with IDEA
 * @author :zyg
 * @date :2021/03/15
 */
@SpringBootApplication(scanBasePackages = {"com.zyg.item.*","com.zyg.core.*"})
@EnableTransactionManagement
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.zyg.item.*"})
@MapperScan(value = "com.zyg.*.mapper")
@EnableApolloConfig
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class);
    }
}
