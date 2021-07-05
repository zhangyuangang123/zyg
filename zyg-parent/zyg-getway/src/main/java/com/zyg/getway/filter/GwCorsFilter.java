package com.zyg.getway.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @program: zyg-parent
 * @description:支持跨域
 * @author: zyg
 * @create: 2020-06-03 15:57
 **/
@Configuration
public class GwCorsFilter {
    @Bean
    public CorsFilter corsWebFilter() {
        //初始化 cors 配置对象
        CorsConfiguration configuration=new CorsConfiguration();

        //允许跨域的域名，如果要携带cookie，不能写*。* 代表所有域名都可以跨域访问
        configuration.addAllowedOrigin("http://192.168.141.131");
        configuration.setAllowCredentials(true); //允许携带cookie
        configuration.addAllowedMethod("*"); //代表所有的请求方法 Get Post Put Delete
        configuration.addAllowedHeader("*"); //允许携带任何头信息

        //初始化 cors 配置源对象
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource=new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
