package com.acloudchina.hacker.njat.dto.order;

import lombok.Data;


/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:13
 * {
 *   "cardCode": "",
 *   "goodCount": "1",
 *   "goodCutPrice": "15",
 *   "goodFkid": "35261274352050235",
 *   "goodId": "203526127434769815600002",
 *   "goodName": "04月24日~18号场-10:00~11:00",
 *   "goodPrice": "15",
 *   "goodType": "1",
 *   "venueSaleId": "311d545ac9204a90a97662c66ac0773a"
 *  }
 **/
@Data
public class GoodOrderDto {
    private String cardCode;
    private String goodCount = "1";
    private String goodCutPrice;
    private String goodFkid;
    private String goodId;
    private String goodName;
    private String goodPrice;
    private String goodType = "1";
    private String venueSaleId;

    /**
     * 根据日期生成商品名称
     * @param date
     * @return
     */
    public static String buildGoodName(String date, String certainVenueName, String startDate, String endDate) {
        StringBuilder stringBuilder = new StringBuilder(23);
        String[] dateSplit = date.split("-");
        stringBuilder.append(dateSplit[1]);
        stringBuilder.append("月");
        stringBuilder.append(dateSplit[2]);
        stringBuilder.append("日");
        stringBuilder.append("~");
        stringBuilder.append(certainVenueName);
        stringBuilder.append("-");
        stringBuilder.append(startDate);
        stringBuilder.append("~");
        stringBuilder.append(endDate);
        return stringBuilder.toString();
    }
}
