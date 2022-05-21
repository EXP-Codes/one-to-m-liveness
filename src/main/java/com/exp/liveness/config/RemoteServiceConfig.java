package com.exp.liveness.config;

import com.exp.liveness.bean.RemoteService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "detected-list", ignoreInvalidFields = true)
public class RemoteServiceConfig {

    private List<RemoteService> remoteServices;

    public RemoteServiceConfig() {
        this.remoteServices = new LinkedList<>();
    }

    public RemoteServiceConfig(List<RemoteService> remoteServices) {
        this.remoteServices = remoteServices;
    }

    public List<RemoteService> getRemoteServices() {
        return remoteServices;
    }

    public RemoteServiceConfig setRemoteServices(List<RemoteService> remoteServices) {
        this.remoteServices = remoteServices;
        return this;
    }
}
