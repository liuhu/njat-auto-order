package com.acloudchina.hacker.njat.dto.venue.order;

import com.acloudchina.hacker.njat.dto.common.ResponseDto;
import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:47
 **/
@Data
public class VenueOrderResponseDto extends ResponseDto {
    private VenueOrderResponseBodyDto body;
}
