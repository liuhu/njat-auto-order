package com.acloudchina.hacker.njat.utils;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 23:27
 **/
public class GetKeyUtils {

    public static String getKey() {
        return "www.wowsport.cn8";
    }

    public static String getKey(String deviceId) {
        return "www.wowsport.cn" + deviceId;
    }
}
