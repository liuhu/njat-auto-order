package com.acloudchina.hacker.njat.service.transport;

import com.acloudchina.hacker.njat.dto.common.Constants;
import com.acloudchina.hacker.njat.dto.venue.*;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderQueryDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseBodyDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseDto;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 23:10
 **/
@Service
public class VenueTransportService extends TransportBaseService {

    /**
     * 获取场馆列表
     * @return
     */
    public VenueListResponseBodyDto getVenueList() {
        VenueListQueryDto queryDto = new VenueListQueryDto();
        VenueListResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_QUERY_LIST, VenueListResponseDto.class);
        if (null == responseDto || null == responseDto.getBody()) {
            return null;
        }
        return responseDto.getBody();
    }

    /**
     * 查询场馆详细信息
     * @param venueId
     * @return
     */
    public VenueEntityResponseBodyDto getVenueEntityInfo(String venueId) {
        VenueEntityQueryDto queryDto = new VenueEntityQueryDto();
        queryDto.setVenueId(venueId);
        VenueEntityResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_INFO, VenueEntityResponseDto.class);
        if (null == responseDto || null == responseDto.getBody()) {
            return null;
        }
        return responseDto.getBody();
    }

    /**
     * 更加时间查询场地订单列表
     * @param date 2019-04-24
     * @param userId
     * @return
     */
    public VenueOrderResponseBodyDto getVenueOrder(String date, String userId) {
        VenueOrderQueryDto queryDto = new VenueOrderQueryDto();
        queryDto.setDate(date);
        queryDto.setUserId(userId);
        VenueOrderResponseDto responseDto = exchange(queryDto, Constants.QUERY_VENUE_SELL_ORDER, VenueOrderResponseDto.class);
        if (null == responseDto || null == responseDto.getBody()) {
            return null;
        }
        return responseDto.getBody();
    }


}
