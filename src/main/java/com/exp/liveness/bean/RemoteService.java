package com.exp.liveness.bean;

import com.exp.liveness.envm.RemoteServiceProtocol;
import com.exp.liveness.envm.SuccessStatus;

// Nested properties need not be annotated
// @ConfigurationProperties(prefix =
// "available-payment-channels-list.channelConfigurations")
// @Configuration
public class RemoteService {

    private String name;

    private String protocol;

    private String address;

    private int statusCode;

    private String statusDesc;

    public RemoteService() {
        this.name = "";
        this.protocol = RemoteServiceProtocol.UNKNOW;
        this.address = "";
        this.statusCode = SuccessStatus.UNKNOW;
        this.statusDesc = "";
    }

    public RemoteService(String name, String protocol, String address, int statusCode, String statusDesc) {
        this.name = name;
        this.protocol = protocol;
        this.address = address;
        this.statusCode = statusCode;
        this.statusDesc = statusDesc;
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
