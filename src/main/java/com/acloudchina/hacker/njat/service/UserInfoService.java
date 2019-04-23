package com.acloudchina.hacker.njat.service;

import com.acloudchina.hacker.njat.dto.user.UserLoginDto;
import com.acloudchina.hacker.njat.dto.user.UserResponseBodyDto;
import com.acloudchina.hacker.njat.service.transport.UserTransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 11:22
 **/
@Service
@Slf4j
public class UserInfoService {

    @Autowired
    private UserTransportService userTransportService;

    /**
     * 缓存用户信息
     * key 手机号
     * value 用户信息
     */
    private Map<String, UserResponseBodyDto> userInfos = new HashMap<>();

    /**
     * 获取用户信息
     * @param phoneNumber
     * @param password
     * @return
     */
    public UserResponseBodyDto getUserInfo(String phoneNumber, String password) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UserResponseBodyDto response = userInfos.get(phoneNumber);
        if (null == response) {
            UserLoginDto loginDto = new UserLoginDto();
            loginDto.setLoginType("1");
            loginDto.setPhoneNumber(phoneNumber);
            loginDto.setPassword(password);
            userInfos.put(phoneNumber, userTransportService.getUserInfo(loginDto));
        }
        return userInfos.get(phoneNumber);
    }
}
