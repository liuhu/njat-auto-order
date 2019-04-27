package com.acloudchina.hacker.njat.controller;

import com.acloudchina.hacker.njat.dto.task.CreateTaskDto;
import com.acloudchina.hacker.njat.dto.task.TaskInfoDto;
import com.acloudchina.hacker.njat.service.TaskService;
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
@RequestMapping("/tasks")
public class OrderController {

    @Autowired
    private TaskService taskService;

    /**
     * 添加
     * @param dto
     * @return
     */
    @PostMapping
    public TaskInfoDto addTask(@RequestBody @Valid CreateTaskDto dto) {
        return taskService.addTask(dto);
    }

    @PostMapping("/clean")
    public void cleanTask() {
        taskService.cleanTask();
    }

    @GetMapping
    public Map<String, TaskInfoDto> getAllTask() {
        return taskService.getAllTask();
    }

        @PostMapping("/panic")
    public TaskInfoDto cleanTask(@RequestBody @Valid CreateTaskDto dto) {
        return taskService.immediatePanic(dto);
    }

}
