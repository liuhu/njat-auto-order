package com.acloudchina.hacker.njat.dto.venue.order;

import lombok.Data;


/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:49
 *
 * {
 *     "startDate": "09:00",
 *     "sellOrderMxId": "34656474648785528",
 *     "endDate": "10:00",
 *     "orderPrice": "15",
 *     "goodId": "203465647464657068000001",
 *     "certainVenueName": "11号场",
 *     "isBook": "0",
 *     "goodType": "1",
 *     "venueSaleId": "903cb7617c284dfc9d2abe6573634c20"
 * }
 **/
@Data
public class SellOrderDto {
    /**
     * 09:00
     */
    private String startDate;

    /**
     * 34656474648785528
     */
    private String sellOrderMxId;

    /**
     * 10:00
     */
    private String endDate;

    /**
     * 15
     */
    private Integer orderPrice;

    /**
     * 203465647464657068000001
     */
    private String goodId;

    /**
     * 11号场
     */
    private String certainVenueName;

    /**
     * 0 未被预定
     * 1 已被预定
     */
    private Integer isBook;

    /**
     * 1
     */
    private Integer goodType;

    /**
     * 903cb7617c284dfc9d2abe6573634c20
     */
    private String venueSaleId;
}
