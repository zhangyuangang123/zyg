package com.zyg.getway.filter;

import com.netflix.zuul.context.RequestContext;
import com.zyg.auth.utils.JwtUtils;
import com.zyg.getway.config.FilterProperties;
import com.zyg.getway.config.JwtProperties;
import com.zyg.getway.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

@Slf4j
@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperties.class})
public class AuthFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtProperties jwtProp;

    @Autowired
    private FilterProperties filterProp;

	@Override
	public int getOrder() {
		return 100;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		String url = exchange.getRequest().getURI().getPath();
        URI requestUrl = exchange.getRequiredAttribute(GATEWAY_REQUEST_URL_ATTR);
        //获取请求URI的请求结构
        String scheme = requestUrl.getScheme();
        log.info("获取请求URI的请求结构：{}",scheme);
		log.info("拦截路径：{}",url);
        for(int i = 0;i<filterProp.getAllowPaths().size();i++) {
            System.out.println(filterProp.getAllowPaths().get(i));
            if(url.startsWith(filterProp.getAllowPaths().get(i))){
                return chain.filter(exchange);
            }
        }

        // 获取token
        //final String token = exchange.getRequest().getHeaders().get("Cookie").get(0);
        // 获取token
        String token = CookieUtils.getCookieValue(exchange.getRequest(), jwtProp.getCookieName());
        // 校验
        try {
            // 校验通过什么都不做，即放行
            JwtUtils.getInfoFromToken(token, jwtProp.getPublicKey());
            return chain.filter(exchange);
        } catch (Exception e) {
            ServerHttpResponse originalResponse = exchange.getResponse();
            originalResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
            byte[] response = "{\"message\": \"未登录或Token过期.\"}"
                    .getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
            return originalResponse.writeWith(Flux.just(buffer));
        }
	}

}
