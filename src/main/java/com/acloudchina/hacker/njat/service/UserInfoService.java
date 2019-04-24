package com.acloudchina.hacker.njat.service;

import com.acloudchina.hacker.njat.dto.card.MyCardPackageResponseBodyDto;
import com.acloudchina.hacker.njat.dto.user.UserInfoDto;
import com.acloudchina.hacker.njat.dto.user.UserLoginDto;
import com.acloudchina.hacker.njat.dto.user.UserResponseBodyDto;
import com.acloudchina.hacker.njat.service.transport.UserTransportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private Map<String, UserInfoDto> userInfos = new HashMap<>();

    /**
     * 获取用户信息
     * @param phoneNumber
     * @param password
     * @return
     */
    public UserInfoDto getUserInfo(String phoneNumber, String password) {
        UserInfoDto response = userInfos.get(phoneNumber);
        if (null == response) {
            UserLoginDto loginDto = new UserLoginDto();
            loginDto.setLoginType("1");
            loginDto.setPhoneNumber(phoneNumber);
            loginDto.setPassword(password);
            // 获取用户信息
            UserResponseBodyDto userDto = userTransportService.getUserInfo(loginDto);
            if (null == userDto
                    || StringUtils.isBlank(userDto.getUserId())) {
                log.error("获取用户信息失败, phoneNumber = {}, password = {}", phoneNumber, password);
                throw new IllegalArgumentException("获取用户信息失败!");
            }

            // 获取卡信息
            MyCardPackageResponseBodyDto cardDto = userTransportService.getUserPayCard(userDto.getUserId());

            if (null == cardDto
                    || null == cardDto.getMyCardPackageList()
                    || cardDto.getMyCardPackageList().isEmpty()
                    || StringUtils.isBlank(cardDto.getMyCardPackageList().get(0).getCardId())) {
                log.error("获取用户支付卡信息失败, phoneNumber = {}, password = {}", phoneNumber, password);
                throw new IllegalArgumentException("获取用户支付卡信息失败!");
            }

            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setUserId(userDto.getUserId());
            userInfoDto.setPayPass(userDto.getPayPass());
            userInfoDto.setPayCardId(cardDto.getMyCardPackageList().get(0).getCardId());
            userInfos.put(phoneNumber, userInfoDto);
        }
        return userInfos.get(phoneNumber);
    }
}
