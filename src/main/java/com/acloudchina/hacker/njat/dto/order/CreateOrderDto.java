package com.acloudchina.hacker.njat.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-23 15:18
 **/
@Data
public class CreateOrderDto {
    /**
     * 订购日期
     */
    @NotBlank
    private String date;

    /**
     * 订购时间段
     */
    private List<String> orderTime = Arrays.asList("19:00", "20:00");

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
     * 获取任务Key
     * @return
     */
    @JsonIgnore
    public String getOrderKey() {
        return phoneNumber + "-" + date;
    }
}
