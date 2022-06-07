package com.exp.liveness.bean;

import com.exp.liveness.envm.LivenessStatus;
import com.exp.liveness.envm.RemoteServiceProtocol;

//嵌套的对象属性不需要注解
//@Configuration
//@ConfigurationProperties(prefix = "detected-list.remoteServices")
public class RemoteService {

    /** 远端服务名 */
    private String name;

    /**
     * 远端服务协议，目前只支持：
     *  http
     *  socket
     */
    private String protocol;

    /**
     * 远端服务的探活地址：
     *  http = url
     *  socket = ip:port
     */
    private String address;

    /** 远端服务返回的状态码 */
    private int statusCode;

    /** 远端服务的状态描述 */
    private String statusDesc;

    public RemoteService() {
        this.name = "";
        this.protocol = RemoteServiceProtocol.UNKNOW;
        this.address = "";
        this.statusCode = LivenessStatus.UNKNOW;
        this.statusDesc = "";
    }

    public RemoteService(String name, String protocol, String address, int statusCode, String statusDesc) {
        this.name = name;
        this.protocol = protocol;
        this.address = address;
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
    }

    public String toSimpleInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(name).append("]");
        sb.append("[").append(protocol).append("]");
        sb.append("[").append(address).append("]");
        return sb.toString();
    }

    public String toDetailInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(name).append("]");
        sb.append("[").append(protocol).append("]");
        sb.append("[").append(address).append("]");
        sb.append("[").append(statusCode).append("]");
        sb.append("[").append(statusDesc).append("]");
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public RemoteService setName(String name) {
        this.name = name;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public RemoteService setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public RemoteService setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public RemoteService setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public RemoteService setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
        return this;
    }

}
