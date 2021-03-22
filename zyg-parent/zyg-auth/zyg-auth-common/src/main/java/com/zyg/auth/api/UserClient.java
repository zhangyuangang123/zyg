package com.zyg.auth.api;

import com.zyg.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}