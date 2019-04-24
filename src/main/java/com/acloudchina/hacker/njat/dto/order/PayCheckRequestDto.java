package com.acloudchina.hacker.njat.dto.order;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-24 11:28
 **/
@Data
public class PayCheckRequestDto {
    private String orderId;
    private String passWord;
    private String paytypeId;
    private String userId;

    private String discountAmoun = "10";
    private String sign;
}
