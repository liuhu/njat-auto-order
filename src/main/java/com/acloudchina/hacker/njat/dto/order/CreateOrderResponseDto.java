package com.acloudchina.hacker.njat.dto.order;

import com.acloudchina.hacker.njat.dto.common.ResponseDto;
import lombok.Data;
import lombok.ToString;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-23 11:22
 **/
@Data
@ToString(callSuper = true)
public class CreateOrderResponseDto extends ResponseDto {
    private CreateOrderResponseBodyDto body;
}
