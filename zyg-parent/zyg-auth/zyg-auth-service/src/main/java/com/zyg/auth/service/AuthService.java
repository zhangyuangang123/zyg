package com.zyg.auth.service;

import com.zyg.auth.api.UserClient;
import com.zyg.auth.config.JwtProperties;
import com.zyg.auth.entity.UserInfo;
import com.zyg.auth.utils.JwtUtils;
import com.zyg.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2020/10/19.
 */
@Service
public class AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties properties;

    public String authentication(String username, String password) {

        try {
            // 调用微服务，执行查询
            User user = this.userClient.queryUser(username, password);

            // 如果查询结果为null，则直接返回null
            if (user == null) {
                return null;
            }

            // 如果有查询结果，则生成token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), user.getUsername()),
                    properties.getPrivateKey(), properties.getExpire());
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
