package com.pass.grpc.server;

import com.pass.grpc.proto.GreeterGrpc;
import com.pass.grpc.proto.HelloReply;
import com.pass.grpc.proto.HelloRequest;
import io.grpc.stub.StreamObserver;

/**
 * grpc--Demo
 *
 * @author yuanzhonglin
 * @date 2019/5/28
 */
public class HelloWorldServer extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String name = request.getName();
        String sex = request.getSex();
        HelloReply helloReply = HelloReply
                .newBuilder()
                .setMessage("Hello " + name + ", your sex is " + sex + "!")
                .build();
        responseObserver.onNext(helloReply);
        responseObserver.onCompleted();
    }
}
