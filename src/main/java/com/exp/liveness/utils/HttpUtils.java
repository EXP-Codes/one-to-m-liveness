package com.exp.liveness.utils;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.envm.LivenessStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpUtils {

    private HttpUtils() {}

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

    private static boolean isLiveness(int statusCode) {
        return statusCode >= LivenessStatus.OK_HTTP_MIN && statusCode <= LivenessStatus.OK_HTTP_MAX;
    }

}
