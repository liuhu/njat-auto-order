package com.acloudchina.hacker.njat.controller;

import com.acloudchina.hacker.njat.dto.user.UserInfoDto;
import com.acloudchina.hacker.njat.dto.venue.VenueEntityResponseBodyDto;
import com.acloudchina.hacker.njat.dto.venue.VenueListResponseBodyDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseBodyDto;
import com.acloudchina.hacker.njat.service.UserInfoService;
import com.acloudchina.hacker.njat.service.transport.VenueTransportService;
import com.acloudchina.hacker.njat.utils.EncryptionUtils;
import com.acloudchina.hacker.njat.utils.GetKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:18
 **/
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private VenueTransportService venueTransportService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 查询场馆信息，和开放的预定时间
     * @return
     */
    @GetMapping(value = "/venueInfo")
    public VenueEntityResponseBodyDto getVenueInfo() {
        VenueListResponseBodyDto responseBodyDto = venueTransportService.getVenueList();
        String venueId = responseBodyDto.getVenueEntityList().get(0).getVenueId();
        return venueTransportService.getVenueEntityInfo(venueId);
    }

    /**
     * 根据时间获取场地订购列表
     * @param date
     * @return
     */
    @GetMapping(value = "/venueOrders")
    public VenueOrderResponseBodyDto getVenueOrder(String date) {
        UserInfoDto dto = userInfoService.getUserInfo("15365010926", "100030");
        return venueTransportService.getVenueOrder(date, dto.getUserId());
    }


    /**
     * 解码测试
     * @param args
     */
    public static void main(String[] args) {
        // otherPayCheck
        String str1 = "558001825f928661a2974a69e56030e8e49a1c7ac61da0e3e5246bfb9aa75fa8f877f60d46395bf43985834cda70de898155f98a5f543ca9f2fd4d578ac4743c33e6c6fff0e0d5431beb87935d812fcac069ec8d9f049f92ec7f605c48a712031cf34db8d8747451d7c6d589afffcf718c8297ac37d18523c711e00998c833fa298d91656cc21bf2bb4df6ba6b70506062c82f27f0f2c4b052aa40d5f7b567f5";
        System.out.println(EncryptionUtils.decodeFromAes(str1,  GetKeyUtils.getKey("8")));
    }
}
