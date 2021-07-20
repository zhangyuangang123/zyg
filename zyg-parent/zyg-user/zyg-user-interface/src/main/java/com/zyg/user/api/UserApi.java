package com.zyg.user.api;


import com.zyg.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2020/10/19.
 */
public interface UserApi {

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("user/query")
    public ResponseEntity<Object> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );

}
