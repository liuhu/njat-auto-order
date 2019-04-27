package com.acloudchina.hacker.njat.dto.task;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-23 15:18
 **/
@Data
public class CreateTaskDto {
    /**
     * 订购日期
     */
    @NotBlank
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private String date;

    /**
     * 订购时间段
     */
    @NotNull
    private List<String> times = Arrays.asList("19:00", "20:00");

    /**
     * 订购用户手机号码
     */
    @NotBlank
    private String phoneNumber;

    /**
     * 订购用户密码
     */
    @NotBlank
    private String password;

    /**
     * 场地类型名称
     * 体育馆,游泳馆
     */
    @NotBlank
    private String venueTypeName;

    /**
     * 场地优先级
     */
    private List<Integer> venuePriority;
}
