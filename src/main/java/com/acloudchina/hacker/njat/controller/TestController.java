package com.acloudchina.hacker.njat.controller;

import com.acloudchina.hacker.njat.dto.user.UserResponseBodyDto;
import com.acloudchina.hacker.njat.dto.venue.VenueEntityResponseBodyDto;
import com.acloudchina.hacker.njat.dto.venue.VenueListResponseBodyDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseBodyDto;
import com.acloudchina.hacker.njat.service.transport.UserTransportService;
import com.acloudchina.hacker.njat.service.transport.VenueTransportService;
import com.acloudchina.hacker.njat.utils.EncryptionUtils;
import com.acloudchina.hacker.njat.utils.GetKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:18
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserTransportService userTransportService;

    @Autowired
    private VenueTransportService venueTransportService;

    @GetMapping(value = "/user")
    public UserResponseBodyDto getUserInfo() {
        return userTransportService.getUserInfo();
    }

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
        return venueTransportService.getVenueOrder(date);
    }


    /**
     * 解码测试
     * @param args
     */
    public static void main(String[] args) {
        String str = "83feb422fa0fb2bfb19a8b72d280725adc4892fe2b2aa28335491c9863c9da4afc2641cb7d84e836caea86ebee01013f7b6dc80de538025df9d55c5ac39dbc5c613d7e5f2343382b1d23a8bf44373cf728c84046788d361646abb95dc2ef9622f438c4e2259d5348545d7f9d76c525b078f795102c3e4a0728af643a6a302734";
        System.out.println(EncryptionUtils.decodeFromAes(str,  GetKeyUtils.getKey("8")));
    }
}
