package org.crashcourse;

import java.util.Optional;

import org.crashcourse.stubs.stubs.HelloWorldRequest;
import org.crashcourse.stubs.stubs.HelloWorldResponse;
import org.crashcourse.stubs.stubs.HelloWorldServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloWorldController extends HelloWorldServiceGrpc.HelloWorldServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldController.class);

    private HelloWorldResponse sayHello(final HelloWorldRequest request) {
        
	log.info("Received request: {}", request);
        
        return HelloWorldResponse
                .newBuilder().setGreeting(
                			"Hello " + Optional.of(request.getName())
                				.map(String::trim)
                				.filter(s -> !s.isEmpty())
                				.orElse("World")+ "!"
                )
                .build();
    }

    @Override
    public StreamObserver<HelloWorldRequest> sayHello(final StreamObserver<HelloWorldResponse> responseObserver) {
        
	return StreamObserverUtility.proxyStream(responseObserver, this::sayHello);
    }
}