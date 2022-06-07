package com.exp.liveness.service.impl;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.bean.Result;
import com.exp.liveness.config.RemoteServiceConfig;
import com.exp.liveness.envm.RemoteServiceProtocol;
import com.exp.liveness.envm.LivenessStatus;
import com.exp.liveness.service.HealthService;
import com.exp.liveness.utils.HttpUtils;
import com.exp.liveness.utils.ResultUtils;
import com.exp.liveness.utils.SocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * 一对多探活接口实现
 * @author exp
 * @date 2022-05-22
 */
@Slf4j
@Service
public class HealthServiceImpl implements HealthService {

    /**
     * 远端服务配置
     */
    @Autowired
    private RemoteServiceConfig remoteServiceConfig;

    /**
     * 对所有远端服务执行探活
     * @return 远端服务探活结果集
     */
    @Override
    public Result<List<RemoteService>> liveness() {
        List<RemoteService> errorRemoteServices = new LinkedList<>();
        List<RemoteService> successRemoteServices = new LinkedList<>();

        List<RemoteService> remoteServices = remoteServiceConfig.getRemoteServices();
        for (RemoteService remoteService : remoteServices) {
            log.info("Detecting Service: {}", remoteService.toSimpleInfo());
            boolean isOK = true;
            switch (remoteService.getProtocol()) {
                case RemoteServiceProtocol.HTTP :
                    isOK = HttpUtils.testLiveness(remoteService);
                    break;

                case RemoteServiceProtocol.SOCKET :
                    isOK = SocketUtils.testLiveness(remoteService);
                    break;

                default:
                    remoteService.
                            setStatusCode(LivenessStatus.UNKNOW).
                            setStatusDesc("Unsupport Protocol");
                    break;
            }

            if (isOK) {
                successRemoteServices.add(remoteService);
                log.info("Detected OK: {}", remoteService.toDetailInfo());
            } else {
                errorRemoteServices.add(remoteService);
                log.warn("Detected ERROR: {}", remoteService.toDetailInfo());
            }
        }
        return ResultUtils.handle(errorRemoteServices, successRemoteServices);
    }
}
