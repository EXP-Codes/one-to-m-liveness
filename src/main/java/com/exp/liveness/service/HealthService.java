package com.exp.liveness.service;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.bean.Result;

import java.util.List;

public interface HealthService {

    public Result<List<RemoteService>> liveness();

}
