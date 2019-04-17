package com.acloudchina.hacker.njat.dto.user;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-16 11:23
 * {
 *     "loginType": "1",
 *     "password": "100030",
 *     "phoneNumber": "15365010926"
 * }
 **/
@Data
public class UserLoginDto {
    private String loginType;
    private String password;
    private String phoneNumber;
}
