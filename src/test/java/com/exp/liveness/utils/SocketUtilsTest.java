package com.exp.liveness.utils;

import com.exp.liveness.GenUnitTestData;
import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.envm.LivenessStatus;
import com.exp.liveness.envm.RemoteServiceProtocol;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class SocketUtilsTest {

    /** 测试用 socket 服务端 */
    private static ServerSocket testSocketServer;

    /** 测试用 socket 客户端代理 */
    private static Socket testSocketClientProxy;

    private static int bindSocketPort;

    @BeforeAll
    static void setUpBeforeClass() throws InterruptedException {

        // 寻找可用的随机端口启用测试服务端
        while (true) {
            try {
                bindSocketPort = GenUnitTestData.genInt(10000, 60000);
                InetSocketAddress socket = (new InetSocketAddress(bindSocketPort));
                testSocketServer = new ServerSocket();
                testSocketServer.bind(socket);
            } catch (Exception e) {
                continue;   // 绑定端口失败
            }
            break;
        }

        // 阻塞等待客户端连接端口
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testSocketClientProxy = testSocketServer.accept();
                } catch (Exception e) {}
            }
        }).start();

        // 确保单元测试在服务端线程启动后才启动
        Thread.sleep(2000);
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        if (testSocketClientProxy != null) {
            testSocketClientProxy.close();
        }
        testSocketServer.close();
    }

    @Test
    void testLivenessWithOK() {
        RemoteService remoteService = new RemoteService();
        remoteService.setName("UT_Service");
        remoteService.setProtocol(RemoteServiceProtocol.SOCKET);
        remoteService.setAddress("127.0.0.1:" + bindSocketPort);

        boolean isOK = SocketUtils.testLiveness(remoteService);
        Assertions.assertTrue(isOK);
        Assertions.assertEquals(LivenessStatus.OK_SOCKET, remoteService.getStatusCode());
    }

    @Test
    void testLivenessWithErr() {
        RemoteService remoteService = new RemoteService();
        remoteService.setName("UT_Service");
        remoteService.setProtocol(RemoteServiceProtocol.SOCKET);
        remoteService.setAddress(GenUnitTestData.genIP() + ":" + GenUnitTestData.genInt(1, 65535));

        boolean isOK = SocketUtils.testLiveness(remoteService);
        Assertions.assertFalse(isOK);
        Assertions.assertEquals(LivenessStatus.UNKNOW, remoteService.getStatusCode());
    }
}