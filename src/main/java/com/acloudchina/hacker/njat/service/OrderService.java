package com.acloudchina.hacker.njat.service;

import com.acloudchina.hacker.njat.config.DynamicConfig;
import com.acloudchina.hacker.njat.dto.order.CreateOrderDto;
import com.acloudchina.hacker.njat.dto.user.UserInfoDto;
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
        // 校验用户信息
        userInfoService.getUserInfo(dto.getPhoneNumber(), dto.getPassword());
        addTaskMap(dto);
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
     * 立刻抢购
     * @param dto
     */
    public void immediatePanicOrder(CreateOrderDto dto) {
        // 校验用户信息
        userInfoService.getUserInfo(dto.getPhoneNumber(), dto.getPassword());
        panicOrder(dto);
    }

    /**
     * 心跳任务, 保持和服务器的通信, 为抢购做准备
     */
    @Scheduled(initialDelay = 5000, fixedRate = 20000)
    public void heartbeatTask() {
        long startTime = System.currentTimeMillis();
        venueTransportService.getVenueList();
        long endTime = System.currentTimeMillis();
        log.info("通信维持{}ms...", endTime - startTime);
    }

    /**
     * 处理抢购任务
     * 每日早晨6点00、01、02、03触发一次, 多次触发防止任务失败的重试
     */
    @Scheduled(zone = "Asia/Shanghai", cron = "0 0,1,2,3 6 * * ?")
    public void dealPanicOrderTask() {
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
                log.info("开始抢购! dto = {}", dto);

                // 获取用户信息
                UserInfoDto userInfo = userInfoService.getUserInfo(dto.getPhoneNumber(), dto.getPassword());
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
                        // 订单创建成功跳出循环
                        break;
                    } catch (Exception e) {
                        log.error("创建订单失败, dto = {}, venueId = {}, e = {}", dto, venueId, e);
                    }
                }

                // 订单创建失败
                if (null == bookNumber) {
                    // 订单创建失败, 移除任务
                    removeTaskMap(dto.getOrderKey());
                    log.error("抢购失败 - 创建订单失败! dto = {}", dto);
                    return;
                }

                // 支付订单
                for (int i = 0; i < PAY_RETRY_COUNT; i++) {
                    try {
                        // 支付成功 移除任务
                        orderTransportService.payOrder(bookNumber, userInfo);
                        removeTaskMap(dto.getOrderKey());
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

    /**
     * 添加任务
     */
    private void addTaskMap(CreateOrderDto dto) {
        taskLockMap.put(dto.getOrderKey(), new ReentrantLock());
        taskMap.put(dto.getOrderKey(), dto);
    }

    /**
     * 移除任务
     * @param key
     */
    private void removeTaskMap(String key) {
        taskLockMap.remove(key);
        taskMap.remove(key);
    }
}
