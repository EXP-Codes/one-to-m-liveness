package com.exp.liveness.envm;

/**
 * 探活状态码
 * @author exp
 * @date 2022-05-22
 */
public class LivenessStatus {

    private LivenessStatus() {}

    /** 未知协议的状态码 */
    public final static int UNKNOW = -1;

    /** socket 协议的 成功状态码 */
    public final static int OK_SOCKET = 0;

    /** http 协议的 成功状态码（最小值） */
    public final static int OK_HTTP_MIN = 200;

    /** http 协议的 成功状态码（最大值） */
    public final static int OK_HTTP_MAX = 399;

}
