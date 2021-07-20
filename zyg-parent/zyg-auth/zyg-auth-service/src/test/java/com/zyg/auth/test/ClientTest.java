package com.zyg.auth.test;

import com.zyg.auth.api.UserClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientTest {
    @Autowired
    private UserClient userClient;

    @Test
    public void userClientQuery(){
        this.userClient.queryUser("zhangsan","123456");
    }
}
