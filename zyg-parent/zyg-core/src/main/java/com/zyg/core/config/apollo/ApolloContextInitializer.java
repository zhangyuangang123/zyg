package com.zyg.core.config.apollo;

import cn.hutool.core.text.StrFormatter;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.core.dto.ApolloConfig;
import com.ctrip.framework.apollo.core.dto.ServiceDTO;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.config.ConfigPropertySourceFactory;
import com.ctrip.framework.apollo.spring.config.PropertySourcesConstants;
import com.ctrip.framework.apollo.spring.util.SpringInjector;
import com.ctrip.framework.apollo.util.http.HttpRequest;
import com.ctrip.framework.apollo.util.http.HttpResponse;
import com.ctrip.framework.apollo.util.http.HttpUtil;
import com.ctrip.framework.foundation.internals.provider.DefaultApplicationProvider;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 需在resources目录下新增META-INF/spring.factories文件，文件内容为：
 * org.springframework.boot.env.EnvironmentPostProcessor=包名.ApolloContextInitializer
 * org.springframework.context.ApplicationContextInitializer=包名.ApolloContextInitializer
 */
@Slf4j
public class ApolloContextInitializer implements EnvironmentPostProcessor, ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * 配置是否执行
     */
    private final static boolean APOLLO_ENABLE = Boolean.valueOf(System.getProperty(ApolloConfigConst.APOLLO_ENABLE, Boolean.TRUE.toString()));

    private final static Set<Config> CONFIGS = Sets.newHashSet();

    static {
        if (APOLLO_ENABLE) {
            log.info("开始加载apollo------namespace");
            // 检查重复配置 spring.factories
            ApolloContextInitializer.checkDuplicate(EnvironmentPostProcessor.class);
        }
    }

    private static void checkDuplicate(Class<EnvironmentPostProcessor> factoryClass) {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> urls = (classLoader != null ?
                    classLoader.getResources(SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION) :
                    ClassLoader.getSystemResources(SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION));
            List<String> apolloContextInitializerList = Lists.newArrayListWithCapacity(5);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String urlStr = url.toString();
                UrlResource resource = new UrlResource(url);
                Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    String factoryClassName = ((String) entry.getKey()).trim();
                    for (String factoryName : org.springframework.util.StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
                        factoryName = factoryName.trim();
                        if (factoryClassName.equals(factoryClass.getName()) && factoryName.equals(ApolloContextInitializer.class.getName())) {
                            apolloContextInitializerList.add(StrFormatter.format("{}#{}", urlStr, factoryName));
                        }
                    }
                }
            }
            Assert.isTrue(apolloContextInitializerList.size() <= 1,
                    StrFormatter.format("项目中发现有{}个{}都配置了{}，重复配置文件路径=>{}，请检查lib目录下是否存在重复配置",
                            apolloContextInitializerList.size(), SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION,
                            ApolloContextInitializer.class.getName(), apolloContextInitializerList.stream().collect(Collectors.joining("，")))
            );
        } catch (IOException e) {
            throw new IllegalArgumentException(StrFormatter.format("加载{}文件异常，请检查是否存在该配置文件", SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION), e);
        }
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        initialize(environment);
    }

    private void initialize(ConfigurableEnvironment environment) {
        if (!APOLLO_ENABLE) {
            return;
        }

        List<ApolloConfig> apolloConfigList = allApolloConfig();
        Assert.notEmpty(apolloConfigList, StrFormatter.format("该项目没有Apollo配置项，如无需使用Apollo，请在VM参数中配置-D{}=false", ApolloConfigConst.APOLLO_ENABLE));
        CompositePropertySource composite = new CompositePropertySource(PropertySourcesConstants.APOLLO_BOOTSTRAP_PROPERTY_SOURCE_NAME);
        ConfigPropertySourceFactory configPropertySourceFactory = SpringInjector.getInstance(ConfigPropertySourceFactory.class);
        for (ApolloConfig apolloConfig : apolloConfigList) {
            Config config = ConfigService.getConfig(apolloConfig.getNamespaceName());
            composite.addPropertySource(configPropertySourceFactory.getConfigPropertySource(apolloConfig.getNamespaceName(), config));
            CONFIGS.add(config);
        }
        environment.getPropertySources().addFirst(composite);
    }

    private List<ApolloConfig> allApolloConfig() {
        DefaultApplicationProvider defaultApplicationProvider = getApplicationProvider();
        String configServiceUri = getConfigServiceUri(defaultApplicationProvider);
        ServiceDTO adminService = getAdminService(configServiceUri);
        return allApolloConfig(defaultApplicationProvider, adminService);
    }

    private List<ApolloConfig> allApolloConfig(DefaultApplicationProvider defaultApplicationProvider, ServiceDTO adminService) {
        String clustersName = System.getProperty(ConfigConsts.APOLLO_CLUSTER_KEY, ConfigConsts.CLUSTER_NAME_DEFAULT);
        if (adminService.getHomepageUrl().endsWith("/")){
            String homepageUrl = adminService.getHomepageUrl();
            adminService.setHomepageUrl(homepageUrl.substring(0,homepageUrl.length()-1));
        }
        String namespacesUrl = StrFormatter.format(ApolloConfigConst.NAMESPACES_URL_PATTERN, adminService.getHomepageUrl(), defaultApplicationProvider.getAppId(), clustersName);
        try {
            HttpUtil httpUtil = new HttpUtil();
            HttpRequest request = new HttpRequest(namespacesUrl);
            request.setConnectTimeout(ApolloConfigConst.TIMEOUT);
            request.setReadTimeout(ApolloConfigConst.TIMEOUT);
            HttpResponse<List<ApolloConfig>> response = httpUtil.doGet(request, new TypeToken<List<ApolloConfig>>() {}.getType());
            return response.getBody();
        } catch (Exception e) {
            throw new IllegalStateException(StrFormatter.format("获取Apollo信息异常，请求url=>{}，请检查配置", namespacesUrl), e);
        }
    }

    private DefaultApplicationProvider getApplicationProvider() {
        DefaultApplicationProvider defaultApplicationProvider = new DefaultApplicationProvider();
        defaultApplicationProvider.initialize();
        return defaultApplicationProvider;
    }

    private ServiceDTO getAdminService(String metaServiceUri) {
        String adminServiceUrl = StrFormatter.format(ApolloConfigConst.ADMIN_SERVICE_URL_PATTERN, metaServiceUri);
        try {
            HttpUtil httpUtil = new HttpUtil();
            HttpRequest request = new HttpRequest(adminServiceUrl);
            request.setConnectTimeout(ApolloConfigConst.TIMEOUT);
            request.setReadTimeout(ApolloConfigConst.TIMEOUT);
            HttpResponse<List<ServiceDTO>> response = httpUtil.doGet(request, new TypeToken<List<ServiceDTO>>() {}.getType());
            List<ServiceDTO> adminServiceList = response.getBody();
            Assert.notEmpty(adminServiceList, StrFormatter.format("无Apollo AdminService服务实例，请检查服务，如无需使用Apollo，请在VM参数中配置-D{}=false", ApolloConfigConst.APOLLO_ENABLE));
            return adminServiceList.get(0);
        } catch (Exception e) {
            throw new IllegalStateException(StrFormatter.format("获取Apollo AdminService服务实例信息异常，请求url=>{}，请检查配置，如无需使用Apollo，请在VM参数中配置-D{}=false", adminServiceUrl, ApolloConfigConst.APOLLO_ENABLE), e);
        }
    }

    private String getConfigServiceUri(DefaultApplicationProvider defaultApplicationProvider) {
        String configServiceUri = System.getProperty(ConfigConsts.APOLLO_META_KEY);
        if (StringUtils.isBlank(configServiceUri)) {
            configServiceUri = defaultApplicationProvider.getProperty(ConfigConsts.APOLLO_META_KEY, StringUtils.EMPTY);
        }
        Assert.hasText(configServiceUri, StrFormatter.format("Apollo ConfigService uri未配置，如无需使用Apollo，请在VM参数中配置-D{}=false", ApolloConfigConst.APOLLO_ENABLE));
        configServiceUri = configServiceUri.indexOf(",") > 0 ? configServiceUri.split(",")[0] : configServiceUri;
        return configServiceUri;
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (CollectionUtils.isEmpty(CONFIGS)) {
            return;
        }
        // 创建 Apollo 监听器
        ConfigRefreshListener configRefreshListener = new ConfigRefreshListener(applicationContext);
        // 所有 Namespace Config 对象添加监听器
        CONFIGS.stream().forEach(config -> config.addChangeListener(configRefreshListener));
    }
}

@AllArgsConstructor
@Slf4j
class ConfigRefreshListener implements ConfigChangeListener {

    private ApplicationContext applicationContext;

    @Override
    public void onChange(ConfigChangeEvent changeEvent) {
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            log.info("Found change - {}", change.toString());
        }
        // 使用 Spring Cloud EnvironmentChangeEvent 刷新配置
        applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
    }
}