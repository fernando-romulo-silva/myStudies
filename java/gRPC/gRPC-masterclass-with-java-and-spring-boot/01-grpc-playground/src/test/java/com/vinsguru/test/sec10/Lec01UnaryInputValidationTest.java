package com.vinsguru.test.sec10;

import com.vinsguru.models.sec10.AccountBalance;
import com.vinsguru.models.sec10.BalanceCheckRequest;
import com.vinsguru.models.sec10.ErrorMessage;
import com.vinsguru.models.sec10.ValidationCode;
import com.vinsguru.test.common.ResponseObserver;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Lec01UnaryInputValidationTest extends AbstractTest {

    @Test
    void blockingInputValidationTest() {
        var ex = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(11)
                    .build();
            this.bankBlockingStub.getAccountBalance(request);
        });
        Assertions.assertEquals(ValidationCode.INVALID_ACCOUNT, getValidationCode(ex));

        final var key = ProtoUtils.keyForProto(ErrorMessage.getDefaultInstance());
        System.out.println(ex.getTrailers().get(key).getValidationCode());
        Status.trailersFromThrowable(ex);
    }

    @Test
    void asyncInputValidationTest() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(11)
                .build();
        var observer = ResponseObserver.<AccountBalance>create();
        this.bankStub.getAccountBalance(request, observer);
        observer.await();

        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertNotNull(observer.getThrowable());
        Assertions.assertEquals(ValidationCode.INVALID_ACCOUNT, getValidationCode(observer.getThrowable()));
    }
}
