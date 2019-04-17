package com.acloudchina.hacker.njat.dto.venue;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 11:14
 * {
 *     "saleDateYr": "04月16日",
 *     "saleDate": "2019-04-16",
 *     "sdate": "04-16",
 *     "venueId": "1124641455777345",
 *     "isBook": "1",
 *     "week": "星期二"
 * }
 **/
@Data
public class SiteSellOrderDto {
    /**
     * 2019-04-16
     */
    private String saleDate;

    /**
     * 1124641455777345
     */
    private String venueId;
}
