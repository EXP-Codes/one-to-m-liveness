package com.exp.liveness.utils;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.envm.LivenessStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * HTTP 客户端工具
 * @author exp
 * @date 2022-05-22
 */
public class HttpUtils {

    private HttpUtils() {}

    /**
     * HTTP 服务探活
     * @param remoteService HTTP 服务
     * @return 服务是否正常
     */
    public static boolean testLiveness(RemoteService remoteService) {
        HttpGet httpGet = new HttpGet(remoteService.getAddress());

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            remoteService.setStatusCode(statusCode);

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
        return statusCode >= LivenessStatus.OK_HTTP_MIN && statusCode <= LivenessStatus.OK_HTTP_MAX;
    }

}
