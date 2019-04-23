package com.acloudchina.hacker.njat.dto.order;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-23 11:21
 **/
@Data
public class CreateOrderResponseBodyDto {
    private String bookNumber;
    private String totalAmount;
}
