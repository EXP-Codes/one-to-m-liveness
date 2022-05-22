package com.exp.liveness.utils;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.envm.LivenessStatus;

import java.net.Socket;

/**
 * Socket 客户端工具
 * @author exp
 * @date 2022-05-22
 */
public class SocketUtils {

    private SocketUtils() {}

    /**
     * Socket 端口探活
     * @param remoteService Socket 服务
     * @return 服务是否正常
     */
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

    /**
     * 判读远端服务返回的状态码是否正常
     * @param statusCode 远端服务返回的状态码
     * @return 服务是否正常
     */
    private static boolean isLiveness(int statusCode) {
        return statusCode == LivenessStatus.OK_SOCKET;
    }

}
