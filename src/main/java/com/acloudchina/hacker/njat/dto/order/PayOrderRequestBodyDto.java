package com.acloudchina.hacker.njat.dto.order;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-23 14:12
 * {
 *   "bookNumber" : "20190415143531768105363110757",
 *   "userId" : "34693021860297337"
 * }
 **/
@Data
public class PayOrderRequestBodyDto {
    private String bookNumber;
    private String userId;
}
