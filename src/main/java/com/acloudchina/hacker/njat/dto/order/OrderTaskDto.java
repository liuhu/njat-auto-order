package com.acloudchina.hacker.njat.dto.order;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-26 17:55
 **/
@Data
public class OrderTaskDto {

    /**
     * 任务ID
     */
    private String orderTaskId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 支付卡id
     */
    private String payCardId;

    /**
     * 支付卡密码
     */
    private String payPass;

    /**
     * 场地ID
     */
    private String venueId;

    /**
     * 场地名称
     */
    private String venueName;

    /**
     * 场地orgCode
     */
    private String orgCode;

    /**
     * 预定日期
     */
    private String date;

    /**
     * 预定时间段, 默认是晚上19:00~21:00
     */
    private List<String> orderTime = Arrays.asList("19:00", "20:00");

    /**
     * 场地优先级
     */
    private List<Integer> venuePriority;
}
