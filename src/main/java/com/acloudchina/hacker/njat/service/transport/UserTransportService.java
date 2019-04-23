package com.acloudchina.hacker.njat.service.transport;

import com.acloudchina.hacker.njat.dto.common.Constants;
import com.acloudchina.hacker.njat.dto.user.UserLoginDto;
import com.acloudchina.hacker.njat.dto.user.UserResponseBodyDto;
import com.acloudchina.hacker.njat.dto.user.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 23:01
 **/
@Service
@Slf4j
public class UserTransportService extends TransportBaseService {

    /**
     * 获取用户信息
     * @return
     */
    public UserResponseBodyDto getUserInfo(UserLoginDto dto) {
        UserResponseDto responseDto = exchange(dto, Constants.QUERY_USER_INFO, UserResponseDto.class);
        return responseDto.getBody();
    }
}
