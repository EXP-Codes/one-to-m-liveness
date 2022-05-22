package com.exp.liveness;

import com.exp.liveness.bean.RemoteService;
import com.exp.liveness.envm.LivenessStatus;
import com.exp.liveness.envm.RemoteServiceProtocol;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 生成单元测试数据
 * @author exp
 * @date 2022-05-22 19:47
 */
public class GenUnitTestData {

    /** 随机对象 */
    private final static Random random = new Random();

    private GenUnitTestData() {}

    /**
     * 生成探活失败的远端服务数据
     * @param size 数据规模
     * @return
     */
    public static List<RemoteService> genErrorRemoteServices(int size) {
        return genRemoteServices(size, true);
    }

    /**
     * 生成探活成功的远端服务数据
     * @param size 数据规模
     * @return
     */
    public static List<RemoteService> genSuccessRemoteServices(int size) {
        return genRemoteServices(size, false);
    }

    private static List<RemoteService> genRemoteServices(int size, boolean isOK) {
        List<RemoteService> remoteServices = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            RemoteService rs = new RemoteService();
            rs.setName("UT_Service_" + i);
            if (i % 2 == 0) {
                rs.setProtocol(RemoteServiceProtocol.HTTP);
                rs.setAddress(genURL());
                rs.setStatusCode(isOK ?
                        genInt(LivenessStatus.OK_HTTP_MIN, LivenessStatus.OK_HTTP_MAX) :    // [200-399]
                        genInt(LivenessStatus.OK_HTTP_MIN));                                // [0,200)
            } else {
                rs.setProtocol(RemoteServiceProtocol.SOCKET);
                rs.setAddress(genIP() + ":" + i);
                rs.setStatusCode(isOK ? LivenessStatus.OK_SOCKET : LivenessStatus.UNKNOW);
            }
            remoteServices.add(rs);
        }
        return remoteServices;
    }

    /**
     * 获取随机 url
     * @return 返回随机数 url
     */
    public static String genURL() {
        StringBuilder url = new StringBuilder();
        url.append("https://").append(genString(5)).append(".com");
        return url.toString();
    }

    /**
     * 获取随机 IP
     * @return 返回随机数 IP
     */
    public static String genIP() {
        StringBuilder ip = new StringBuilder();
        int A = genInt(1, 255);
        int B = genInt(0, 255);
        int C = genInt(0, 255);
        int D = genInt(0, 255);
        ip.append(A).append(".").append(B).append(".").append(C).append(".").append(D);
        return ip.toString();
    }

    /**
     * 获取 int 随机数
     * @param min 随机数限界最小值
     * @param max 随机数限界最大值
     * @return 返回随机数范围 [min,max]
     */
    public static int genInt(final int min, final int max) {
        int num = genInt(max - min + 1);
        return num + min;
    }

    /**
     * 获取 int 随机数
     * @param scope 随机数限界
     * @return 返回随机数范围 [0,scope)
     */
    private static int genInt(int scope) {
        return (scope <= 0 ? 0 : random.nextInt(scope));
    }

    /**
     * 随机生成一个 0-9A-Za-z 范围内的字符串
     * @param len 字符串长度
     * @return 随机字符串
     */
    private static String genString(int len) {
        len = len < 1 ? 1 : len;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(genCharacter());
        }
        return sb.toString();
    }

    /**
     * 随机生成一个 0-9A-Za-z 内的字符
     * @return 返回随机单词字符
     */
    private static char genCharacter() {
        int[] scope = genRange();
        int idx = genInt(scope.length);
        return (char) scope[idx];
    }

    /**
     * 生成随机字符的范围
     * @return 随机字符的范围
     */
    private static int[] genRange() {
        int digit = genInt(48, 57);    // 0-9
        int upper = genInt(65, 90);    // A-Z
        int lower = genInt(97, 122);    // a-z
        return new int[] { digit, upper, lower };
    }

}
