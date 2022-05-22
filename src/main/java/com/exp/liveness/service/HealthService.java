package com.exp.liveness.service;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.bean.Result;

import java.util.List;

/**
 * 一对多探活接口定义
 * @author exp
 * @date 2022-05-22
 */
public interface HealthService {

    /**
     * 对所有远端服务执行探活
     * @return 远端服务探活结果集
     */
    public Result<List<RemoteService>> liveness();

}
