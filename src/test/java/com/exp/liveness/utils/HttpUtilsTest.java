package com.exp.liveness.utils;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.envm.RemoteServiceProtocol;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class HttpUtilsTest {

    private final static String CHARSET = "UTF-8";

    private final static String RESPONSE_BODY_ALL_OK = "/com/exp/liveness/utils/HttpUtilsTest-AllOK.json";

    private final static String RESPONSE_BODY_ANY_OK = "/com/exp/liveness/utils/HttpUtilsTest-AnyOK.json";

    private final static String RESPONSE_BODY_ALL_ERR = "/com/exp/liveness/utils/HttpUtilsTest-AllErr.json";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testLivenessWithAnyOK() throws Exception {
        String jsonAnyOK = IOUtils.toString(this.getClass().getResourceAsStream(RESPONSE_BODY_ANY_OK), CHARSET);
        CloseableHttpClient mockHttpClient = mockAnyHttpRequest(true, HttpStatus.SC_BAD_GATEWAY, jsonAnyOK, CHARSET);
        ReflectionTestUtils.invokeMethod(HttpUtils.class, "setMockHttpClient", mockHttpClient);

        RemoteService rs = new RemoteService();
        rs.setName("UT_Service_01").setProtocol(RemoteServiceProtocol.HTTP).setAddress("https://baidu.com");
        boolean isOK = HttpUtils.testLiveness(rs);
        System.out.println(isOK);
        System.out.println(rs.getStatusCode());
    }

    /**
     * 模拟对外发起的任意 HTTP 请求
     * @param getORpost 请求方法 true:HttpGet.class 或 false:HttpPost.class
     * @param httpStatus 模拟响应状态码 HttpStatus
     * @param responseBody 模拟响应返回内容 Entity
     * @return  mockHttpClient: 用于替换被测方法的 HttpClient 以拦截请求
     * @throws Exception
     */
    private static CloseableHttpClient mockAnyHttpRequest(boolean getORpost, int httpStatus,
                                           String responseBody, String responseCharset) throws Exception {
        CloseableHttpClient mockHttpClient = Mockito.mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = Mockito.mock(CloseableHttpResponse.class);
        StatusLine mockStatusLine = Mockito.mock(StatusLine.class);

        Mockito.when(mockHttpClient.execute(
                getORpost ? Mockito.any(HttpGet.class) : Mockito.any(HttpPost.class)
        )).thenReturn(mockHttpResponse);
        Mockito.when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
        Mockito.when(mockStatusLine.getStatusCode()).thenReturn(httpStatus);
        Mockito.when(mockHttpResponse.getEntity()).thenReturn(
                new StringEntity(responseBody, StandardCharsets.UTF_8)
        );
        return mockHttpClient;
    }


}