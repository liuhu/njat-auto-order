package com.acloudchina.hacker.njat.dto.order;

import com.acloudchina.hacker.njat.dto.common.Constants;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:13
 * {
 *     "goodOrderList": [
 *         {
 *             "cardCode": "",
 *             "goodCount": "1",
 *             "goodCutPrice": "15",
 *             "goodFkid": "35261274352050235",
 *             "goodId": "203526127434769815600002",
 *             "goodName": "04月24日~18号场-10:00~11:00",
 *             "goodPrice": "15",
 *             "goodType": "1",
 *             "venueSaleId": "311d545ac9204a90a97662c66ac0773a"
 *         },
 *         {
 *             "cardCode": "",
 *             "goodCount": "1",
 *             "goodCutPrice": "15",
 *             "goodFkid": "35261274354179094",
 *             "goodId": "203526127434769815600003",
 *             "goodName": "04月24日~18号场-11:00~12:00",
 *             "goodPrice": "15",
 *             "goodType": "1",
 *             "venueSaleId": "311d545ac9204a90a97662c66ac0773a"
 *         }
 *     ],
 *     "isCollect": "0",
 *     "orgCode": "60fca1582822456bbd5d77f719daca3e",
 *     "paySource": "1",
 *     "sourceFkId": "1124641455777345",
 *     "sourceName": "副馆羽毛球",
 *     "totalAmount": "30.0",
 *     "totalCutAmount": "30.0",
 *     "userId": "34693021860297337"
 * }
 **/
@Data
public class CreateOrderRequestBodyDto {
    private List<GoodOrderDto> goodOrderList;
    private String isCollect = "0";
    /**
     * VenueEntityDto.orgCode
     */
    private String orgCode;
    private String paySource = "1";
    /**
     * VenueEntityDto.venueId
     */
    private String sourceFkId;
    /**
     * VenueEntityDto.venueName
     */
    private String sourceName;
    /**
     * 15.0
     */
    private String totalAmount;

    /**
     * 15.0
     */
    private String totalCutAmount;

    /**
     * 34693021860297337
     */
    private String userId;
}
