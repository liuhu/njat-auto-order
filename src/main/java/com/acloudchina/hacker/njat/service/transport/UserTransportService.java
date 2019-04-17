package com.acloudchina.hacker.njat.service.transport;

import com.acloudchina.hacker.njat.config.DynamicConfig;
import com.acloudchina.hacker.njat.dto.common.Constants;
import com.acloudchina.hacker.njat.dto.user.UserLoginDto;
import com.acloudchina.hacker.njat.dto.user.UserResponseBodyDto;
import com.acloudchina.hacker.njat.dto.user.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 23:01
 **/
@Service
@Slf4j
public class UserTransportService extends TransportBaseService {

    @Autowired
    private DynamicConfig dynamicConfig;

    /**
     * 获取用户信息
     * @return
     */
    public UserResponseBodyDto getUserInfo() {
        UserLoginDto userQueryDto = new UserLoginDto();
        userQueryDto.setLoginType(dynamicConfig.getLoginType());
        userQueryDto.setPhoneNumber(dynamicConfig.getPhoneNumber());
        userQueryDto.setPassword(dynamicConfig.getPassword());
        UserResponseDto responseDto = exchange(userQueryDto, Constants.QUERY_USER_INFO, UserResponseDto.class);
        return responseDto.getBody();
    }
}
