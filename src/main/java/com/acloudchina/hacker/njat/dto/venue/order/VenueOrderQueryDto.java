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
     * 场馆
     */
    private String venueId = "1124641455777345";

    /**
     * 查询时间
     * 2019-04-17
     */
    private String date = "2019-04-20";

    /**
     * 用户ID
     * 34693021860297337
     */
    private String userId = "34693021860297337";
}
