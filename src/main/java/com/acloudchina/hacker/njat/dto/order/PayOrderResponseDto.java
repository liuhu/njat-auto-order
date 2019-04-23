package com.acloudchina.hacker.njat.dto.order;

import com.acloudchina.hacker.njat.dto.common.ResponseDto;
import lombok.Data;
import lombok.ToString;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-23 14:16
 **/
@Data
@ToString(callSuper = true)
public class PayOrderResponseDto extends ResponseDto {
    private PayOrderResponseBodyDto body;
}
