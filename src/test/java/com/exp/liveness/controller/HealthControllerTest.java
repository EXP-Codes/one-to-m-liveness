package com.exp.liveness.controller;

import com.exp.liveness.GenUnitTestData;
import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.bean.Result;
import com.exp.liveness.service.HealthService;
import com.exp.liveness.utils.ResultUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)   // 配置注入 MockMvc，不启动 filter
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthService healthService;

    private final static String LIVENESS_URI = "/health/liveness";

    private final static Result<List<RemoteService>> UT_ALL_OK_RESULT = ResultUtils.handle(
            GenUnitTestData.genErrorRemoteServices(0),
            GenUnitTestData.genSuccessRemoteServices(10)
    );

    private final static Result<List<RemoteService>> UT_ANY_OK_RESULT = ResultUtils.handle(
            GenUnitTestData.genErrorRemoteServices(5),
            GenUnitTestData.genSuccessRemoteServices(5)
    );

    private final static Result<List<RemoteService>> UT_ALL_ERR_RESULT = ResultUtils.handle(
            GenUnitTestData.genErrorRemoteServices(10),
            GenUnitTestData.genSuccessRemoteServices(0)
    );

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void livenessWithAllOK() throws Exception {
        /**
         * 使用 Mockito 预设 HealthService 的行为：
         *  当调用 HealthService.liveness() 时必定返回 UT_ALL_OK_RESULT。
         *  使得在调用 HealthController.liveness() 时，不会真正与底层交互。
         */
        Mockito.when(healthService.liveness()).thenReturn(UT_ALL_OK_RESULT);

        // 通过 mockMvc 对 HealthController.liveness() 接口模拟前端发起请求
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(LIVENESS_URI)
        );

        // 校验返回结果（这里相当于断言）
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void livenessWithAnyOK() throws Exception {
        Mockito.when(healthService.liveness()).thenReturn(UT_ANY_OK_RESULT);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(LIVENESS_URI)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().is(HttpStatus.PARTIAL_CONTENT.value()));
    }

    @Test
    void livenessWithAllError() throws Exception {
        Mockito.when(healthService.liveness()).thenReturn(UT_ALL_ERR_RESULT);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get(LIVENESS_URI)
        );
        resultActions.andExpect(MockMvcResultMatchers.status().is(HttpStatus.PARTIAL_CONTENT.value()));
    }
}