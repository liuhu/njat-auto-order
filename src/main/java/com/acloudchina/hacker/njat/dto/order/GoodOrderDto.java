package com.acloudchina.hacker.njat.dto.order;

import lombok.Data;


/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:13
 **/
@Data
public class GoodOrderDto {
    private String cardCode;
    private Integer goodCount = 1;
    private Integer goodCutPrice;
    private String goodFkid;
    private String goodId;
    private String goodName;
    private String goodPrice;
    private Integer goodType = 1;
    private String venueSaleId;
}
