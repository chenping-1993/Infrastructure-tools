package com.example.cp.scheduled.tasks;

import com.example.cp.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: 定时任务类
 * @Author: chenping
 * @Date: 2020-05-21
 */
@Slf4j
@Component
public class AssignPlayersTasks {

    @Autowired
    MatchService service;

//    @Scheduled(fixedDelay = 1000L)
    public void auditingTask() {
//        service.match(id);
    }

}
