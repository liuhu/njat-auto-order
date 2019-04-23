package com.acloudchina.hacker.njat.service;

import com.acloudchina.hacker.njat.config.DynamicConfig;
import com.acloudchina.hacker.njat.dto.order.CreateOrderDto;
import com.acloudchina.hacker.njat.dto.user.UserResponseBodyDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseBodyDto;
import com.acloudchina.hacker.njat.service.transport.OrderTransportService;
import com.acloudchina.hacker.njat.service.transport.VenueTransportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-23 14:25
 **/
@Service
@Slf4j
public class OrderService {

    @Autowired
    private VenueTransportService venueTransportService;

    @Autowired
    private OrderTransportService orderTransportService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DynamicConfig dynamicConfig;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    private static final int PAY_RETRY_COUNT = 5;

    /**
     * 任务列表
     * key
     */
    private Map<String, CreateOrderDto> taskMap = new ConcurrentHashMap<>();

    /**
     * 任务锁, 防止任务重复执行
     */
    private Map<String, ReentrantLock> taskLockMap = new ConcurrentHashMap<>();

    /**
     * 添加任务, 一个用户在只能添加一天添加一条任务, 否则会被覆盖
     * @param dto
     * @return
     */
    public Map<String, CreateOrderDto> addTask(CreateOrderDto dto) {
        taskLockMap.put(dto.getOrderKey(), new ReentrantLock());
        taskMap.put(dto.getOrderKey(), dto);
        return taskMap;
    }

    /**
     * 清除任务
     */
    public void cleanTask() {
        taskLockMap.clear();
        taskMap.clear();
    }

    /**
     * 获取全部任务
     * @return
     */
    public Map<String, CreateOrderDto> getAllTask() {
        return taskMap;
    }

    /**
     * 处理订单任务
     */
    @Scheduled(initialDelay = 5000, fixedRate = 6000)
    public void dealOrderTask() {
        taskMap.values().forEach(
                x -> threadPoolExecutor.execute(() -> panicOrder(x))
        );
    }

    /**
     * 抢购
     * @param dto
     */
    private void panicOrder(CreateOrderDto dto) {
        ReentrantLock lock = taskLockMap.get(dto.getOrderKey());
        try {
            if (lock.tryLock()) {
                log.info("Do order.........");

                // 获取用户信息
                UserResponseBodyDto userInfo = userInfoService.getUserInfo(dto.getPhoneNumber(), dto.getPassword());
                if (null == userInfo || StringUtils.isBlank(userInfo.getUserId())) {
                    log.error("获取用户信息异常, dto = {}", dto);
                    return;
                }

                // 获取场地信息
                VenueOrderResponseBodyDto orderResponse = venueTransportService.getVenueOrder(dto.getDate(), userInfo.getUserId());
                if (null == orderResponse
                        || null == orderResponse.getSellOrderMap()
                        || orderResponse.getSellOrderMap().isEmpty()) {
                    log.warn("[{}]的羽毛球场地还未开放预定, 或获取场地失败. dto = {}", dto.getDate(), dto);
                    return;
                }

                // 根据场地优先级创建订单
                String bookNumber = null;
                for (Integer venueId : dynamicConfig.getVenuePriority()) {
                    try {
                        bookNumber = orderTransportService.createOrder(dto, userInfo.getUserId(), orderResponse.getSellOrderMap().get(venueId));
                    } catch (Exception e) {
                        log.error("创建订单失败, dto = {}, venueId = {}, e = {}", dto, venueId, e);
                    }
                }

                // 订单创建失败
                if (null == bookNumber) {
                    // 订单创建失败, 移除任务
                    taskLockMap.remove(dto.getOrderKey());
                    taskMap.remove(dto.getOrderKey());
                    log.error("抢购失败 - 创建订单失败! dto = {}", dto);
                    return;
                }

                // 支付订单
                for (int i = 0; i < PAY_RETRY_COUNT; i++) {
                    try {
                        // 支付成功 移除任务
                        orderTransportService.payOrder(bookNumber, userInfo.getUserId());
                        taskLockMap.remove(dto.getOrderKey());
                        taskMap.remove(dto.getOrderKey());
                        log.info("抢购成功! user = {}, date = {}", dto.getPhoneNumber(), dto.getDate());
                        break;
                    } catch (Exception e) {
                        log.error("支付失败, 重试第{}次, dto = {},  e = {}", i, dto, e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("抢购任务异常, dto = {}, e = {}", dto, e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
