package com.exp.liveness.config;

import com.exp.liveness.Tester;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan
@EnableConfigurationProperties
class RemoteServiceConfigTest {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(Tester.class).run(args);
        RemoteServiceConfig rsConfig = context.getBean(RemoteServiceConfig.class);
        rsConfig.getRemoteServices().forEach(remoteService ->
                System.out.println(
                        remoteService.getName() + "\t" +
                        remoteService.getProtocol() + "\t" +
                        remoteService.getAddress())
        );
    }

}