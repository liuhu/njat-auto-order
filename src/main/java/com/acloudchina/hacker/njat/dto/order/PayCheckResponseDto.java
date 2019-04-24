package com.acloudchina.hacker.njat.dto.order;

import com.acloudchina.hacker.njat.dto.common.ResponseDto;
import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-24 11:32
 **/
@Data
public class PayCheckResponseDto extends ResponseDto {
    private PayCheckResponseBodyDto body;
}
