package com.exp.liveness.utils;

import com.exp.liveness.bean.RemoteService;

public class HttpUtils {

    private HttpUtils() {}

    public static boolean testLiveness(RemoteService remoteService) {
        // FIXME
        remoteService.setStatusCode(203);
        remoteService.setStatusDesc("xxxxx");
        return true;
    }
}
