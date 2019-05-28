package com.pass.grpc.common;

import com.pass.grpc.server.build.HelloWorldServerBuild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author yuanzhonglin
 * @date 2019/5/28
 */
@Component
public class ServerApplicationRunning implements ApplicationRunner {

    @Autowired
    private HelloWorldServerBuild helloWorldServerBuild;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        helloWorldServerBuild.start();
        helloWorldServerBuild.blockUntilShutdown();
    }
}
