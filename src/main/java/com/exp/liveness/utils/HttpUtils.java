package com.exp.liveness.utils;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.envm.LivenessStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * HTTP 客户端工具
 * @author exp
 * @date 2022-05-22
 */
@Slf4j
public class HttpUtils {

    /** 测试用的 HttpClient */
    private static CloseableHttpClient mockHttpClient;

    private HttpUtils() {}

    /**
     * HTTP 服务探活
     * @param remoteService HTTP 服务
     * @return 服务是否正常
     */
    public static boolean testLiveness(RemoteService remoteService) {
        HttpGet httpGet = new HttpGet(remoteService.getAddress());

        try (CloseableHttpClient httpClient = getHttpClient()) {
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            remoteService.setStatusCode(statusCode);

        } catch (Exception e) {
            log.error("Remote HTTP Server Error", e);
            remoteService.setStatusCode(LivenessStatus.UNKNOW);
            remoteService.setStatusDesc(e.getMessage());
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

    /**
     * 仅用于单元测试 （此方法限制只能通过反射调用，避免误用）
     * @param mockHttpClient 测试用的 HttpClient
     */
    private static void setMockHttpClient(CloseableHttpClient mockHttpClient) {
        HttpUtils.mockHttpClient = mockHttpClient;
    }

    /**
     * 当单元测试用的 HttpClient 不为空时，使用它替换正常的 HttpClient 以模拟测试
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        return HttpUtils.mockHttpClient == null ? HttpClients.createDefault() : HttpUtils.mockHttpClient;
    }

}
