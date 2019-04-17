package com.acloudchina.hacker.njat.dto.common;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:54
 * {
 *     "IPAddress": "",
 *     "accessToken": "03d9b855842a527b6f3f75092b9ab70950fab127da294097979f096291bb5e38",
 *     "appIdentifier": "com.njat.product",
 *     "appVersion": "1.1.2",
 *     "devModuleID": "iPhone",
 *     "deviceId": "B5C51A43-96D2-49ED-8D52-00B7E8DF3A0E",
 *     "deviceType": "2",
 *     "osVersion": "12.2",
 *     "retMessage": "查询可预订场馆",
 *     "retStatus": "0",
 *     "workBench": "0"
 * }
 **/
@Data
public class ResponseHeaderDto {

    private String IPAddress;
    /**
     * 03d9b855842a527b6f3f75092b9ab70950fab127da294097979f096291bb5e38
     */
    private String accessToken;

    /**
     * com.njat.product
     */
    private String appIdentifier;

    /**
     * 1.1.2
     */
    private String appVersion;

    /**
     * iPhone
     */
    private String devModuleID;

    /**
     * B5C51A43-96D2-49ED-8D52-00B7E8DF3A0E
     */
    private String deviceId;

    /**
     * 2
     */
    private String deviceType;

    /**
     * 12.2
     */
    private String osVersion;

    /**
     * 查询可预订场馆
     */
    private String retMessage;

    /**
     * 0 成功
     * 1 失败
     */
    private Integer retStatus;

    /**
     * 0
     */
    private Integer workBench;
}
