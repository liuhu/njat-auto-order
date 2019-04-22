package com.acloudchina.hacker.njat.dto.venue;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-22 20:52
 * [
 *     {
 *         "venueAddress": "南京奥体中心C62号停车柱",
 *         "price": "10.00",
 *         "venuePicPath": "http://app.njaoti.com/api/loadpic.jsp?path=/olympic_files/focus/fgaaa.jpg",
 *         "venueId": "1124641455777345",
 *         "venueName": "副馆羽毛球"
 *     }
 * ]
 **/
@Data
public class VenueListResponseBodyDto {
    private List<VenueListEntityDto> venueEntityList;
}
