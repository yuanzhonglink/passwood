package com.pass.grpc.client;

import com.pass.grpc.proto.GreeterGrpc;
import com.pass.grpc.proto.HelloReply;
import com.pass.grpc.proto.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author yuanzhonglin
 * @date 2019/5/28
 */
@Service
@Slf4j
public class HelloWorldClient {

    @Value("${grpc.ip}")
    private String ip;

    @Value("${grpc.port}")
    private int port;

    private ManagedChannel channel;
    private GreeterGrpc.GreeterBlockingStub blockingStub;

    @PostConstruct
    public void init() {
        channel = ManagedChannelBuilder.forAddress(ip, port)
                .usePlaintext(true)
                .build();

        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public String sayHello() {
        HelloRequest.Builder builder = HelloRequest.newBuilder();
        builder.setName("pass");
        builder.setSex("boy");
        HelloReply helloReply = blockingStub.sayHello(builder.build());
        return helloReply.getMessage();
    }
}
