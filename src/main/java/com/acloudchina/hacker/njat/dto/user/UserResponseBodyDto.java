package com.acloudchina.hacker.njat.dto.user;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 10:44
 * {
 *     "userCount": "1",
 *     "sex": "1",
 *     "userHeadPath": "http://app.njaoti.com/api/loadpic.jsp?path=/olympic_files/pic/head_man11.jpg",
 *     "payPass": "100030",
 *     "birthDay": "",
 *     "userWeight": "65kg",
 *     "QRCodePath": "",
 *     "userHeight": "175cm",
 *     "phoneNumber": "",
 *     "isAuth": "1",
 *     "nickName": "",
 *     "deliveryAddressId": "",
 *     "name": "",
 *     "userId": "",
 *     "userStatus": "0",
 *     "IDcard": ""
 * }
 **/
@Data
public class UserResponseBodyDto {
    private String IDcard;
    private String QRCodePath;
    private String birthDay;
    private String deliveryAddressId;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String sex;
    private String userHeadPath;
    private String userHeight;
    private String userId;
    private String userStatus;
    private String userWeight;
}
