package com.exp.liveness.envm;

public class LivenessStatus {

    private LivenessStatus() {}

    public final static int UNKNOW = -1;

    public final static int OK_SOCKET = 0;

    public final static int OK_HTTP_MIN = 200;

    public final static int OK_HTTP_MAX = 399;

}
