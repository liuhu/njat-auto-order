package com.acloudchina.hacker.njat.dto.card;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-24 10:25
 **/
@Data
public class MyCardQueryRequestDto {
    private String cardType = "1";
    private String pageNum = "1";
    private String pageSize = "10";

    private String userId;
}
