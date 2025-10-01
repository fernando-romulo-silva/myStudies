package com.vinsguru.test.sec12;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vinsguru.common.GrpcServer;
import com.vinsguru.models.sec12.BalanceCheckRequest;
import com.vinsguru.sec12.Constants;
import com.vinsguru.sec12.UserRoleBankService;
import com.vinsguru.sec12.interceptors.UserRoleInterceptor;

import io.grpc.CallCredentials;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;

class Lec07UserRoleContextTest extends AbstractInterceptorTest {

    private static final Logger log = LoggerFactory.getLogger(Lec07UserRoleContextTest.class);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return Collections.emptyList();
    }

    @Override
    protected GrpcServer createServer() {
        return GrpcServer.create(6565, builder -> {
            builder.addService(new UserRoleBankService())
                    .intercept(new UserRoleInterceptor());
        });
    }

    @Test
    void unaryUserCredentialsDemo() {
        for (int i = 1; i <= 4; i++) {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(i)
                    .build();

            var response = this.bankBlockingStub
                    .withCallCredentials(new UserSessionToken("user-token-" + i))
                    .getAccountBalance(request);

            log.info("{}", response);
        }
    }

    private static class UserSessionToken extends CallCredentials {

        private static final String TOKEN_FORMAT = "%s %s";
        private final String jwt;

        public UserSessionToken(String jwt) {
            this.jwt = jwt;
        }

        @Override
        public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier metadataApplier) {
            executor.execute(() -> {
                var metadata = new Metadata();
                metadata.put(Constants.USER_TOKEN_KEY, TOKEN_FORMAT.formatted(Constants.BEARER, jwt));
                metadataApplier.apply(metadata);
            });
        }

    }

}
