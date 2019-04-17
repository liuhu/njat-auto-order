package com.acloudchina.hacker.njat.service.transport;

import com.acloudchina.hacker.njat.dto.common.Constants;
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


    public VenueOrderResponseBodyDto getVenue() {
        VenueOrderQueryDto queryDto = new VenueOrderQueryDto();
        queryDto.setUserId(userInfoService.getUserInfo().getUserId());
        VenueOrderResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_SELL_ORDER, VenueOrderResponseDto.class);
        return responseDto.getBody();
    }
}
