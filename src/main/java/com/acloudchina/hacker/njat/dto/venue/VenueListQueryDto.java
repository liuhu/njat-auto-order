package com.acloudchina.hacker.njat.dto.venue;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-22 20:45
 **/
@Data
public class VenueListQueryDto {
    private String latitude = "31.9772690000";
    private String longitude = "118.7746210000";
    private String pageNum = "1";
    private String pageSize = "10";
    /**
     * 001 是指羽毛球场地
     */
    private String venueTypeCode;
}
