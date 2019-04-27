package com.acloudchina.hacker.njat.service;

import com.acloudchina.hacker.njat.dto.user.UserInfoDto;
import com.acloudchina.hacker.njat.dto.user.UserLoginDto;
import com.acloudchina.hacker.njat.dto.user.UserResponseBodyDto;
import com.acloudchina.hacker.njat.service.transport.UserTransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
     * 获取用户信息
     * @param phoneNumber
     * @param password
     * @return
     */
    public UserInfoDto getUserInfo(String phoneNumber, String password) {
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setLoginType("1");
        loginDto.setPhoneNumber(phoneNumber);
        loginDto.setPassword(password);
        // 获取用户信息
        UserResponseBodyDto userDto = userTransportService.getUserInfo(loginDto);
        // 获取卡信息
        String payCardId = userTransportService.getUserPayCard(userDto.getUserId());
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUserId(userDto.getUserId());
        userInfoDto.setPayPass(userDto.getPayPass());
        userInfoDto.setPayCardId(payCardId);
        return userInfoDto;
    }
}
