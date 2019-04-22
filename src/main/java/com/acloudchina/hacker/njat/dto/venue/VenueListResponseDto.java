package com.acloudchina.hacker.njat.dto.venue;

import com.acloudchina.hacker.njat.dto.common.ResponseDto;
import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-22 20:56
 **/
@Data
public class VenueListResponseDto extends ResponseDto {
    private VenueListResponseBodyDto body;
}
