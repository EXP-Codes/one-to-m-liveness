package com.exp.liveness.utils;

import com.exp.liveness.GenUnitTestData;
import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.bean.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ResultUtilsTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void handleWithAllOK() {
        List<RemoteService> errorRemoteServices = GenUnitTestData.genErrorRemoteServices(0);
        List<RemoteService> successRemoteServices = GenUnitTestData.genErrorRemoteServices(10);
        Result<List<RemoteService>> result = ResultUtils.handle(errorRemoteServices, successRemoteServices);
        Assertions.assertTrue(result.isOK());
    }

    @Test
    void handleWithAnyOK() {
        List<RemoteService> errorRemoteServices = GenUnitTestData.genErrorRemoteServices(5);
        List<RemoteService> successRemoteServices = GenUnitTestData.genErrorRemoteServices(5);
        Result<List<RemoteService>> result = ResultUtils.handle(errorRemoteServices, successRemoteServices);
        Assertions.assertFalse(result.isOK());
    }

    @Test
    void handleWithAllError() {
        List<RemoteService> errorRemoteServices = GenUnitTestData.genErrorRemoteServices(10);
        List<RemoteService> successRemoteServices = GenUnitTestData.genErrorRemoteServices(0);
        Result<List<RemoteService>> result = ResultUtils.handle(errorRemoteServices, successRemoteServices);
        Assertions.assertFalse(result.isOK());
    }
}