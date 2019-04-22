package com.acloudchina.hacker.njat.service.transport;

import com.acloudchina.hacker.njat.dto.common.Constants;
import com.acloudchina.hacker.njat.dto.venue.*;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderQueryDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseBodyDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseDto;
import com.acloudchina.hacker.njat.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 23:10
 **/
@Service
public class VenueTransportService extends TransportBaseService {

    @Autowired
    private UserInfoService userInfoService;


    /**
     * 获取场馆列表
     * @return
     */
    public VenueListResponseBodyDto getVenueList() {
        userInfoService.login();
        VenueListQueryDto queryDto = new VenueListQueryDto();
        VenueListResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_QUERY_LIST, VenueListResponseDto.class);
        return responseDto.getBody();
    }

    /**
     * 查询场馆详细信息
     * @param venueId
     * @return
     */
    public VenueEntityResponseBodyDto getVenueEntityInfo(String venueId) {
        userInfoService.login();
        VenueEntityQueryDto queryDto = new VenueEntityQueryDto();
        queryDto.setVenueId(venueId);
        VenueEntityResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_INFO, VenueEntityResponseDto.class);
        return responseDto.getBody();
    }

    /**
     * 更加时间查询场地订单列表
     * @param date 2019-04-24
     * @return
     */
    public VenueOrderResponseBodyDto getVenueOrder(String date) {
        VenueOrderQueryDto queryDto = new VenueOrderQueryDto();
        queryDto.setDate(date);
        queryDto.setUserId(userInfoService.login().getUserId());
        VenueOrderResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_SELL_ORDER, VenueOrderResponseDto.class);
        return responseDto.getBody();
    }


}
