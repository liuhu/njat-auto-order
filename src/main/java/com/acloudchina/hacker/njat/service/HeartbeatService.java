package com.acloudchina.hacker.njat.service;

import com.acloudchina.hacker.njat.service.transport.VenueTransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-27 09:23
 **/
@Service
@Slf4j
public class HeartbeatService {

    @Autowired
    private VenueTransportService venueTransportService;

    /**
     * 心跳任务, 保持和服务器的通信
     */
    @Scheduled(cron = "36 15,35,55 * * * ?")
    public void heartbeatTask() {
        venueTransportService.getVenueTypeCodeByName("游泳馆");
    }
}
