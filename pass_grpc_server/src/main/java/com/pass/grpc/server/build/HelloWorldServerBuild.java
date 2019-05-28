package com.pass.grpc.server.build;

import com.pass.grpc.server.HelloWorldServer;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author yuanzhonglin
 * @date 2019/5/28
 */
@Component
public class HelloWorldServerBuild {

    private int port = 50051;
    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService((BindableService) new HelloWorldServer())
                .build()
                .start();

        // 添加钩子
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                HelloWorldServerBuild.this.stop();
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
