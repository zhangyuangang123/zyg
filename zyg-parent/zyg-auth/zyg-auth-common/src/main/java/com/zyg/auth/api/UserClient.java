package com.zyg.auth.api;

import com.zyg.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "zyg-user")
public interface UserClient extends UserApi {
}