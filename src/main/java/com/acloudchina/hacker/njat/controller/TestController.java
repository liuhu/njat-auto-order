package com.acloudchina.hacker.njat.controller;

import com.acloudchina.hacker.njat.dto.user.UserResponseBodyDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseBodyDto;
import com.acloudchina.hacker.njat.service.transport.UserTransportService;
import com.acloudchina.hacker.njat.service.transport.VenueTransportService;
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

    @GetMapping(value = "/venueOrders")
    public VenueOrderResponseBodyDto getVenueOrder() {
        return venueTransportService.getVenue();
    }
}
