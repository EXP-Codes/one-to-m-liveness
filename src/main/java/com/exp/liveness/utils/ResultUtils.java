package com.exp.liveness.utils;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.bean.Result;

import java.util.List;

public class ResultUtils {

    private ResultUtils() {}

    public static Result<List<RemoteService>> handle(
            List<RemoteService> errorRemoteServices,
            List<RemoteService> successRemoteServices) {
        int errorNum = errorRemoteServices.size();
        int successNum = successRemoteServices.size();
        int total = errorNum + successNum;
        boolean isOK = (errorNum <= 0);
        return new Result<>(isOK, total, errorNum, errorRemoteServices, successRemoteServices);
    }

}