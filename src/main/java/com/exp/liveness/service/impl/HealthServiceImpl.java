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

@Slf4j
@Service
public class HealthServiceImpl implements HealthService {

    @Autowired
    private RemoteServiceConfig remoteServiceConfig;

    @Override
    public Result<List<RemoteService>> liveness() {
        List<RemoteService> errorRemoteServices = new LinkedList<>();
        List<RemoteService> successRemoteServices = new LinkedList<>();

        List<RemoteService> remoteServices = remoteServiceConfig.getRemoteServices();
        for (RemoteService remoteService : remoteServices) {
            log.info("正在测试： {}", remoteService.toSimpleInfo());
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
                log.info("服务正常： {}", remoteService.toSimpleInfo());
            } else {
                errorRemoteServices.add(remoteService);
                log.warn("服务异常： {}", remoteService.toSimpleInfo());
            }
        }
        return ResultUtils.handle(errorRemoteServices, successRemoteServices);
    }
}
