package com.acloudchina.hacker.njat.service.transport;

import com.acloudchina.hacker.njat.dto.card.MyCardPackageResponseDto;
import com.acloudchina.hacker.njat.dto.card.MyCardQueryRequestDto;
import com.acloudchina.hacker.njat.dto.common.Constants;
import com.acloudchina.hacker.njat.dto.user.UserLoginDto;
import com.acloudchina.hacker.njat.dto.user.UserResponseBodyDto;
import com.acloudchina.hacker.njat.dto.user.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        if (null == responseDto
                || null == responseDto.getBody()
                || StringUtils.isBlank(responseDto.getBody().getUserId())) {
            log.error("获取用户信息失败, dto = {}", dto);
            throw new RuntimeException("获取用户信息失败!");
        }
        return responseDto.getBody();
    }

    /**
     * 查询用户支付卡ID
     * @param userId
     * @return
     */
    public String getUserPayCard(String userId) {
        MyCardQueryRequestDto requestDto = new MyCardQueryRequestDto();
        requestDto.setUserId(userId);
        MyCardPackageResponseDto responseDto = exchange(requestDto, Constants.QUERY_USER_CARD_INFO, MyCardPackageResponseDto.class);
        if (null == responseDto
                || null == responseDto.getBody()
                || null == responseDto.getBody().getMyCardPackageList()
                || responseDto.getBody().getMyCardPackageList().isEmpty()
                || StringUtils.isBlank(responseDto.getBody().getMyCardPackageList().get(0).getCardId())) {
            log.error("获取用户支付卡信息失败, userId = {}", userId);
            throw new RuntimeException("获取用户支付卡信息失败!");
        }
        return responseDto.getBody().getMyCardPackageList().get(0).getCardId();
    }
}
