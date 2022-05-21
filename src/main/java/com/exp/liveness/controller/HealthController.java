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

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private HealthService healthService;

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
