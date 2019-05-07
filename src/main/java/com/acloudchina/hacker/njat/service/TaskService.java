package com.acloudchina.hacker.njat.service;

import com.acloudchina.hacker.njat.config.DynamicConfig;
import com.acloudchina.hacker.njat.dto.task.CreateTaskDto;
import com.acloudchina.hacker.njat.dto.task.TaskInfoDto;
import com.acloudchina.hacker.njat.dto.user.UserInfoDto;
import com.acloudchina.hacker.njat.dto.venue.VenueEntityDto;
import com.acloudchina.hacker.njat.dto.venue.VenueListEntityDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderQueryDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseBodyDto;
import com.acloudchina.hacker.njat.service.transport.OrderTransportService;
import com.acloudchina.hacker.njat.service.transport.VenueTransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-23 14:25
 **/
@Service
@Slf4j
public class TaskService {

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

    /**
     * 时间格式化
     */
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 支付重试次数
     */
    private static final int PAY_RETRY_COUNT = 5;

    /**
     * 任务列表
     * key
     */
    private Map<String, TaskInfoDto> taskMap = new ConcurrentHashMap<>();

    /**
     * 添加任务, 一个用户在只能添加一天添加一条任务, 否则会被覆盖
     *
     * @param dto
     * @return
     */
    public TaskInfoDto addTask(CreateTaskDto dto) {
        // 校验用户信息
        UserInfoDto userInfo = userInfoService.getUserInfo(dto.getPhoneNumber(), dto.getPassword());
        // 获取场地code
        String venueCode = venueTransportService.getVenueTypeCodeByName(dto.getVenueTypeName());
        // 获取场馆列表信息
        VenueListEntityDto venueListEntityDto = venueTransportService.getVenueInfoByCode(venueCode);
        // 获取场馆详细
        VenueEntityDto venueEntityDto = venueTransportService.getVenueEntityInfo(venueListEntityDto.getVenueId());

        //生成并添加任务
        TaskInfoDto taskInfoDto = new TaskInfoDto();
        taskInfoDto.setTaskId(generateTaskId(dto.getPhoneNumber(), dto.getDate(), venueEntityDto.getVenueId()));
        taskInfoDto.setUserId(userInfo.getUserId());
        taskInfoDto.setPayCardId(userInfo.getPayCardId());
        taskInfoDto.setPayPass(userInfo.getPayPass());
        taskInfoDto.setVenueId(venueEntityDto.getVenueId());
        taskInfoDto.setVenueName(venueEntityDto.getVenueName());
        taskInfoDto.setOrgCode(venueEntityDto.getOrgCode());
        taskInfoDto.setDate(dto.getDate());
        taskInfoDto.setTimes(dto.getTimes());
        taskInfoDto.setAutoPay(dto.isAutoPay());
        if (null != dto.getVenuePriority() && !dto.getVenuePriority().isEmpty()) {
            taskInfoDto.setVenuePriority(dto.getVenuePriority());
        } else {
            taskInfoDto.setVenuePriority(dynamicConfig.getVenuePriority(venueCode));
        }
        addTaskMap(taskInfoDto);
        return taskInfoDto;
    }

    /**
     * 清除任务
     */
    public void cleanTask() {
        taskMap.clear();
    }

    /**
     * 获取全部任务
     *
     * @return
     */
    public Map<String, TaskInfoDto> getAllTask() {
        return taskMap;
    }

    /**
     * 立刻抢购
     *
     * @param dto
     */
    public TaskInfoDto immediatePanic(CreateTaskDto dto) {
        TaskInfoDto taskInfoDto = addTask(dto);
        panic(taskInfoDto);
        return taskInfoDto;
    }

    /**
     * 处理抢购任务
     * 每日早晨6点触发, 多次触发防止任务失败的重试
     */
    @Scheduled(zone = "Asia/Shanghai", cron = "${schedule.autoPanicOrder.cron}")
    public void panicTask() {
        taskMap.values().forEach(
                x -> {
                    // 抢购日期提前两天
                    String dateStr = LocalDateTime.now().plusDays(2).format(DATE_TIME_FORMATTER);
                    if (dateStr.equals(x.getDate())) {
                        threadPoolExecutor.execute(() -> panic(x));
                    } else {
                        log.info("还未到达抢购时间, x = {}", x);
                    }
                }
        );
    }

    /**
     * 抢购
     * @param taskDto
     */
    private void panic(TaskInfoDto taskDto) {
        try {

            log.info("开始抢购! taskDto = {}", taskDto);

            // 获取场地信息
            VenueOrderQueryDto venueOrderQueryDto = new VenueOrderQueryDto();
            venueOrderQueryDto.setUserId(taskDto.getUserId());
            venueOrderQueryDto.setDate(taskDto.getDate());
            venueOrderQueryDto.setVenueId(taskDto.getVenueId());
            VenueOrderResponseBodyDto orderResponse = venueTransportService.getVenueOrder(venueOrderQueryDto);
            if (null == orderResponse
                    || null == orderResponse.getSellOrderMap()
                    || orderResponse.getSellOrderMap().isEmpty()) {
                log.warn("[{}]-[{}]的场地还未开放预定, 或获取场地失败. dto = {}", taskDto.getDate(), taskDto.getVenueName(), taskDto);
                return;
            }

            // 根据场地优先级创建订单
            String bookNumber = null;
            for (Integer venueId : taskDto.getVenuePriority()) {
                try {
                    bookNumber = orderTransportService.createOrder(taskDto, orderResponse.getSellOrderMap().get(venueId));
                    // 订单创建成功跳出循环
                    break;
                } catch (Exception e) {
                    log.error("创建订单失败, venueName = {}, taskDto = {}, e = {}", taskDto.getVenueName(), taskDto, e);
                }
            }

            // 订单创建失败
            if (null == bookNumber) {
                // 订单创建失败, 移除任务
                removeTaskMap(taskDto.getTaskId());
                log.error("抢购失败 - 创建订单失败! taskDto = {}", taskDto);
                return;
            }

            if (taskDto.isAutoPay()) {
                // 支付订单
                for (int i = 0; i < PAY_RETRY_COUNT; i++) {
                    try {
                        // 支付成功 移除任务
                        orderTransportService.payOrder(bookNumber, taskDto);
                        removeTaskMap(taskDto.getTaskId());
                        log.info("抢购成功! taskDto = {}", taskDto);
                        break;
                    } catch (Exception e) {
                        log.error("支付失败, 重试第{}次, taskDto = {},  e = {}", i, taskDto, e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("抢购任务异常, taskDto = {}, e = {}", taskDto, e);
        }
    }

    /**
     * 添加任务
     */
    private void addTaskMap(TaskInfoDto dto) {
        taskMap.put(dto.getTaskId(), dto);
    }

    /**
     * 移除任务
     *
     * @param key
     */
    private void removeTaskMap(String key) {
        taskMap.remove(key);
    }

    /**
     * 生成任务ID
     *
     * @param phoneNumber
     * @param date
     * @param venueId
     * @return
     */
    private String generateTaskId(String phoneNumber, String date, String venueId) {
        return phoneNumber + "-" + date + "-" + venueId;
    }
}
