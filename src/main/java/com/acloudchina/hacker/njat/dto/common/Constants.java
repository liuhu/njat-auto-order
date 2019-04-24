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
     * 查询用户信息, 获取userId下单用
     */
    public static final String QUERY_USER_CARD_INFO = BASE_URL + "/api/mine/queryMyCardPackageList.action";

    /**
     * 查询产地信息  VenueEntityDto
     */
    public static final String QUERY_VENUE_INFO = BASE_URL + "/api/venueApp/queryVenueInfo.action";

    /**
     * 查询羽毛球场地购买列表, 获取商品信息下单用
     */
    public static final String QUERY_VENUE_SELL_ORDER = BASE_URL + "/api/venueApp/queryVenueSellOrder.action";

    /**
     * 查询场地列表
     */
    public static final String QUERY_VENUE_QUERY_LIST = BASE_URL + "/api/venueApp/queryVenueList.action";

    /**
     * 创建订单
     */
    public static final String CREATE_ORDER = BASE_URL + "/api/orderApp/createOrder.action";

    /**
     * 支付订单
     */
    public static final String PAY_ORDER = BASE_URL + "/api/paymentApp/paymentOrder.action";

    /**
     * 支付校验
     */
    public static final String PAY_CHECK = BASE_URL + "/api/orderApp/otherPayCheck.action";

    public static final String ORG_CODE = "60fca1582822456bbd5d77f719daca3e";
    public static final String VENUE_ID = "1124641455777345";
    public static final String VENUE_NAME = "副馆羽毛球";


    public static final String DEVICE_ID = "867527034912920";
    public static final String ACCESS_TOKEN = "03d9b855842a527b6f3f75092b";

}
