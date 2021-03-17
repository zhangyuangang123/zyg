package com.zyg.core.config.runner;

import com.zyg.core.utils.RunnerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @program: hgs_parent
 * @description:服务启动监听
 * @author: ShiYulong
 * @create: 2020-03-01 17:06
 **/
@Component
public class ApplicationStartupRunner implements ApplicationRunner {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigurableApplicationContext context;

    @Value("${server.port:}")
    private String port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        InetAddress address = InetAddress.getLocalHost();
        RunnerUtil.showLog(context,contextPath,port,address,logger);
    }
}
