package com.zyg.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.InetAddress;

/**
 * @program: hgs_parent
 * @description: 服务运行状态监听工具类
 * @author: ShiYulong
 * @create: 2020-03-01 18:06
 **/
public class RunnerUtil {
    public static void showLog(ConfigurableApplicationContext context, String contextPath, String port, InetAddress address, Logger logger) {
        if (context.isActive()) {
            String url = String.format("http://%s:%s", address.getHostAddress(), port);
            if (StringUtils.isNotBlank(contextPath)) {
                url += contextPath;
            }
            logger.info("服务启动完毕，地址：{}", url);
        }
    }
}
