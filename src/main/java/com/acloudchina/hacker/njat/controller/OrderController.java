package com.acloudchina.hacker.njat.controller;

import com.acloudchina.hacker.njat.dto.order.CreateOrderDto;
import com.acloudchina.hacker.njat.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-23 22:37
 **/
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/tasks")
    public Map<String, CreateOrderDto> addTask(@RequestBody @Valid CreateOrderDto dto) {
        return orderService.addTask(dto);
    }

    @PostMapping("/tasks/clean")
    public void cleanTask() {
        orderService.cleanTask();
    }

    @GetMapping("/tasks")
    public Map<String, CreateOrderDto> getAllTask() {
        return orderService.getAllTask();
    }

    @PostMapping("/panic")
    public void cleanTask(@RequestBody @Valid CreateOrderDto dto) {
        orderService.immediatePanicOrder(dto);
    }

}
