package com.exp.liveness.controller;


import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.bean.Result;
import com.exp.liveness.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 一对多探活接口
 * @author exp
 * @date 2022-05-22
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

    /**
     * 对所有远端服务执行探活
     * @return json（探活状态总集）：
     *          若全部远端服务探活成功，则此接口的 http 状态码为 200
     *          若至少有 1 个远端服务探活失败，则此接口的 http 状态码为 206
     */
    @GetMapping("liveness")
    public ResponseEntity<Result<List<RemoteService>>> liveness() {
        Result<List<RemoteService>> result = healthService.liveness();

        if (result.isOK()) {
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(result);
        }
    }

}
