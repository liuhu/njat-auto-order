package com.acloudchina.hacker.njat.service;

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

    private UserResponseBodyDto responseBodyDto;

    /**
     * 获取用户信息
     * @return
     */
    public UserResponseBodyDto getUserInfo() {
        if (null == responseBodyDto) {
            responseBodyDto = userTransportService.getUserInfo();
        }
        return responseBodyDto;
    }
}
