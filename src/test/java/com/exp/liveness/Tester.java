package com.exp.liveness;

import com.exp.liveness.config.RemoteServiceConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties
public class Tester {
    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(Tester.class).run(args);
        RemoteServiceConfig rsc = context.getBean(RemoteServiceConfig.class);
        rsc.getRemoteServices().forEach(remoteService ->
                System.out.println(
                        remoteService.getName() + "\t" +
                        remoteService.getProtocol() + "\t" +
                        remoteService.getAddress())
        );
    }
}