package com.exp.liveness.utils;

import com.exp.liveness.bean.RemoteService;

public class SocketUtils {

    private SocketUtils() {}

    public static boolean testLiveness(RemoteService remoteService) {
        // FIXME
        remoteService.setStatusCode(2);
        remoteService.setStatusDesc("yyyy");
        return false;
    }


}
