package com.acloudchina.hacker.njat.dto.card;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-24 10:29
 **/
@Data
public class MyCardPackageResponseBodyDto {
    private List<MyCardPackageDto> myCardPackageList;
}
