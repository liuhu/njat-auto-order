package com.acloudchina.hacker.njat.dto.order;

import com.acloudchina.hacker.njat.dto.common.Constants;
import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-23 14:12
 * {
 *     "bookNumber": "20190424095233535107488018800",
 *     "couponId": "",
 *     "discountAmoun": "10",
 *     "haveBody": "0",
 *     "orgCode": "60fca1582822456bbd5d77f719daca3e",
 *     "payType": "6",
 *     "paytypeId": "0013403058",
 *     "userId": "34693021860297337"
 * }
 **/
@Data
public class PayOrderRequestBodyDto {
    private String bookNumber;
    private String paytypeId;
    private String userId;


    private String couponId;
    private String discountAmoun = "10";
    private String haveBody = "0";
    private String orgCode = Constants.ORG_CODE;
    private String payType = "6";
}
