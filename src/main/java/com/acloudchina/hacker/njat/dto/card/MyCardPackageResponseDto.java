package com.acloudchina.hacker.njat.dto.card;

import com.acloudchina.hacker.njat.dto.common.ResponseDto;
import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-24 10:30
 **/
@Data
public class MyCardPackageResponseDto extends ResponseDto {
    private MyCardPackageResponseBodyDto body;
}
