package com.acloudchina.hacker.njat.dto.venue;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-22 21:26
 **/
@Data
public class VenueEntityResponseBodyDto {
    private VenueEntityDto venueEntity;
    private List<SiteSellOrderDto> siteSellOrderList;
}
