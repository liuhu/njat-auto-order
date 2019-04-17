package com.acloudchina.hacker.njat.dto.common;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 10:25
 **/
public class Constants {
    public static final String BASE_URL = "http://app.njaoti.com";
    /**
     * 查询用户信息, 获取userId下单用
     */
    public static final String QUERY_USER_INFO = BASE_URL + "/api/userManageApp/queryLoginUserInfo.action";

    /**
     * 查询产地信息  VenueEntityDto
     */
    public static final String QUERY_VENUE_INFO = BASE_URL + "/api/venueApp/queryVenueInfo.action";

    /**
     * 查询羽毛球场地购买列表, 获取商品信息下单用
     */
    public static final String QUERY_VENUE_SELL_ORDER = BASE_URL + "/api/venueApp/queryVenueSellOrder.action";

    public static final String DEVICE_ID = "867527034982920";
    public static final String ACCESS_TOKEN = "03d9b855842a527b6f3f75092b9ab70950fab127da294097979f096291bb5e38";

}
