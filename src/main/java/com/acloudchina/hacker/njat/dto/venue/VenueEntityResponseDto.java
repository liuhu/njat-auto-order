package com.acloudchina.hacker.njat.dto.venue;

import com.acloudchina.hacker.njat.dto.common.ResponseDto;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-22 21:20
 * {
 *     "body": {
 *         "picMapList": [
 *             {
 *                 "venuePicTitle": "",
 *                 "venuePicPath": "http://app.njaoti.com/api/loadpic.jsp?path=/olympic_files/focus/fgaaa.jpg"
 *             }
 *         ],
 *         "venueEntity": {
 *             "contactNumber": "025-86690456",
 *             "isCrossBuy": "0",
 *             "reservationNotice": "预订即同意：预订成功后30分钟内,可免费办理退款;开场前48小时外办理退款,返还已支付费用的80%;场地开场前2小时-48小时期间办理退款,返还已支付费用50%;场地开场前2小时内不予办理退款;",
 *             "busInfo": "513路,d7路至奥体北门; 7路;126路至金陵图书馆; 85路;109路至奥体中心东北门",
 *             "maxSelectNum": "3",
 *             "venueDetail": "早上6:00新场地开放！周六、周日及法定节假日：9:00-21:00,60元/小时;体育馆主馆因大型活动需要实行封闭管理，请广大市民从G130入口（冰场入口旁）进入体育馆副馆健身。体育馆主馆部分时段对外开放！",
 *             "subwayInfo": "2号线奥体东站; 10号线奥体中心站",
 *             "advanceDays": "3",
 *             "venueAddress": "南京奥体中心C62号停车柱",
 *             "orgCode": "60fca1582822456bbd5d77f719daca3e",
 *             "shareUrl": "",
 *             "longitude": "118.7250885343",
 *             "venueService": "羽毛球租赁 小型店铺 停车费缴纳",
 *             "saleForm": "0",
 *             "latitude": "32.0065416829",
 *             "venueName": "副馆羽毛球",
 *             "venueId": "1124641455777345"
 *         },
 *         "siteSellOrderList": [
 *             {
 *                 "saleDateYr": "04月15日",
 *                 "saleDate": "2019-04-15",
 *                 "sdate": "04-15",
 *                 "venueId": "1124641455777345",
 *                 "isBook": "1",
 *                 "week": "星期一"
 *             },
 *             {
 *                 "saleDateYr": "04月16日",
 *                 "saleDate": "2019-04-16",
 *                 "sdate": "04-16",
 *                 "venueId": "1124641455777345",
 *                 "isBook": "1",
 *                 "week": "星期二"
 *             },
 *             {
 *                 "saleDateYr": "04月17日",
 *                 "saleDate": "2019-04-17",
 *                 "sdate": "04-17",
 *                 "venueId": "1124641455777345",
 *                 "isBook": "1",
 *                 "week": "星期三"
 *             }
 *         ]
 *     },
 *     "header": {
 *         "IPAddress": "",
 *         "accessToken": "0",
 *         "appIdentifier": "",
 *         "appVersion": "1.1.3",
 *         "devModuleID": "",
 *         "deviceId": "",
 *         "deviceType": "0",
 *         "osVersion": "27",
 *         "retMessage": "查询场馆详情",
 *         "retStatus": "0",
 *         "workBench": "0"
 *     }
 * }
 **/
@Data
public class VenueEntityResponseDto extends ResponseDto {
    private VenueEntityResponseBodyDto body;
}
