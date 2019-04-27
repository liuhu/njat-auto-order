package com.acloudchina.hacker.njat.dto.venue.order;

import lombok.Data;

/**
 * @description: 场地查询
 * @author: LiuHu
 * @create: 2019-04-15 22:43
 **/
@Data
public class VenueOrderQueryDto {
    /**
     * 场馆ID
     */
    private String venueId;

    /**
     * 查询时间
     */
    private String date;

    /**
     * 用户ID
     */
    private String userId;
}
