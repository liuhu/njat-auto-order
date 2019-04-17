package com.acloudchina.hacker.njat.dto.common;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 23:06
 **/
@Data
public class RequestHeaderDto {
    private String retMessage = "";
    private String workBench = "0";
    private String accessToken = Constants.ACCESS_TOKEN;
    private String osVersion = "12.2";
    private String deviceId = Constants.DEVICE_ID;
    private String deviceType = "2";
    private String cityCode = "410300";
    private String appIdentifier = "com.njat.product";
    private String currentCityCode = "";
    private String sysCode = "";
    private String devModuleID = "iPhone";
    private String orgCode = "";
    private String retStatus = "";
    private String appVersion = "1.1.2";
}
