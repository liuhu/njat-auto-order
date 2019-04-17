package com.acloudchina.hacker.njat.dto.venue.order;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:48
 **/
@Data
public class VenueOrderResponseBodyDto {
    private Map<Integer, List<SellOrderDto>> sellOrderMap;
}
