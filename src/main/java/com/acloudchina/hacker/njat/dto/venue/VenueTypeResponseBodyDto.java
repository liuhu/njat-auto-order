package com.acloudchina.hacker.njat.dto.venue;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-26 15:35
 * {
 *     "venueTypeList": [
 *         {
 *             "venueTypeCode": "025",
 *             "venueTypeName": "乒乓球馆"
 *         },
 *         {
 *             "venueTypeCode": "022",
 *             "venueTypeName": "奥体冰上俱乐部"
 *         },
 *         {
 *             "venueTypeCode": "003",
 *             "venueTypeName": "网球中心"
 *         },
 *         {
 *             "venueTypeCode": "001",
 *             "venueTypeName": "体育馆"
 *         },
 *         {
 *             "venueTypeCode": "006",
 *             "venueTypeName": "全民健身园"
 *         },
 *         {
 *             "venueTypeCode": "002",
 *             "venueTypeName": "游泳馆"
 *         },
 *         {
 *             "venueTypeCode": "026",
 *             "venueTypeName": "射箭馆"
 *         }
 *     ]
 * }
 **/
@Data
public class VenueTypeResponseBodyDto {
    private List<VenueTypeDto> venueTypeList;
}
