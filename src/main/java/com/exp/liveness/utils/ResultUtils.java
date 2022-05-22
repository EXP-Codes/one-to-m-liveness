package com.exp.liveness.utils;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.bean.Result;

import java.util.List;

/**
 * 处理返回结果工具
 * @author exp
 * @date 2022-05-22
 */
public class ResultUtils {

    private ResultUtils() {}

    /**
     * 处理远端服务的返回结果集
     * @param errorRemoteServices 异常数据集
     * @param successRemoteServices 正常数据集
     * @return 通用结果对象
     */
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