package com.exp.liveness.utils;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.envm.LivenessStatus;

import java.net.Socket;

public class SocketUtils {

    private final static int SOCKET_TIMEOUT = 10;

    private SocketUtils() {}

    public static boolean testLiveness(RemoteService remoteService) {
        String[] args = remoteService.getAddress().split(":");
        try {
            String ip = args[0];
            int port = Integer.parseInt(args[1]);

            try (Socket socket = new Socket(ip, port)) {
                remoteService.setStatusCode(LivenessStatus.OK_SOCKET);
                socket.close();

            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            remoteService.setStatusCode(LivenessStatus.UNKNOW);
            remoteService.setStatusDesc("Detecte Error");
        }
        return isLiveness(remoteService.getStatusCode());
    }

    private static boolean isLiveness(int statusCode) {
        return statusCode == LivenessStatus.OK_SOCKET;
    }

}
