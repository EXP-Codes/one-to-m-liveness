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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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
            } else {
                errorRemoteServices.add(remoteService);
            }
        }
        return ResultUtils.handle(errorRemoteServices, successRemoteServices);
    }
}
