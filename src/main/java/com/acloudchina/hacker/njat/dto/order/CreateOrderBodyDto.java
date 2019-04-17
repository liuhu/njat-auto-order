package com.acloudchina.hacker.njat.dto.order;

import com.acloudchina.hacker.njat.dto.common.Constants;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:13
 **/
@Data
public class CreateOrderBodyDto {
    private List<GoodOrderDto> goodOrderList;
    private Integer isCollect = 0;
    /**
     * VenueEntityDto.orgCode
     */
    private String orgCode = "60fca1582822456bbd5d77f719daca3e";
    private Integer paySource = 1;
    /**
     * VenueEntityDto.venueId
     */
    private String sourceFkId = "1124641455777345";
    /**
     * VenueEntityDto.venueName
     */
    private String sourceName = "副馆羽毛球";
    /**
     * 15.0
     */
    private BigDecimal totalAmount;

    /**
     * 15.0
     */
    private BigDecimal totalCutAmount;

    /**
     * 34693021860297337
     */
    private String userId;
}
