package com.zyg.core.config.apollo;

/**
 * @program: zyg-parent
 * @description:
 * @author: zyg
 * @create: 2021-03-17
 **/
public interface ApolloConfigConst {

    int TIMEOUT = 3000;
    String APOLLO_ENABLE = "apollo.enable";
    String ADMIN_SERVICE_URL_PATTERN = "{}/services/admin";
    String NAMESPACES_URL_PATTERN = "{}/apps/{}/clusters/{}/namespaces";
}
