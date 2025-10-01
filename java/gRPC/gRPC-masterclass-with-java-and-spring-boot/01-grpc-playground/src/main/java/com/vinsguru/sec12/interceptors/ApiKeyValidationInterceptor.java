package com.vinsguru.sec12.interceptors;

import com.vinsguru.sec12.Constants;
import io.grpc.*;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiKeyValidationInterceptor implements ServerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ApiKeyValidationInterceptor.class);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> serverCall,
            Metadata metadata,
            ServerCallHandler<ReqT, RespT> serverCallHandler) {

        log.info("Method name: {}", serverCall.getMethodDescriptor().getFullMethodName());

        var apiKey = metadata.get(Constants.API_KEY);
        if (isValid(apiKey)) {
            return serverCallHandler.startCall(serverCall, metadata);
        }
        serverCall.close(
                Status.UNAUTHENTICATED.withDescription("client must provide valid api key"),
                metadata);
        return new ServerCall.Listener<ReqT>() {
        };
    }

    private boolean isValid(String apiKey) {
        return Objects.nonNull(apiKey) && apiKey.equals("bank-client-secret");
    }

}
