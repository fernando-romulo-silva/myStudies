package com.vinsguru.test.sec08;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vinsguru.common.GrpcServer;
import com.vinsguru.models.sec08.GuessNumberGrpc;
import com.vinsguru.sec08.GuessNumberService;
import com.vinsguru.test.common.AbstractChannelTest;

/*
    It is simply a demo class
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GuessANumberTest extends AbstractChannelTest {

    private static final Logger log = LoggerFactory.getLogger(GuessANumberTest.class);
    private final GrpcServer server = GrpcServer.create(new GuessNumberService());
    private GuessNumberGrpc.GuessNumberStub stub;

    @BeforeAll
    void setup() {
        this.server.start();
        this.stub = GuessNumberGrpc.newStub(channel);
    }

    @RepeatedTest(5)
    void guessANumberGame() {
        var responseObserver = new GuessResponseHandler();
        var requestObserver = this.stub.makeGuess(responseObserver);
        responseObserver.setRequestObserver(requestObserver);
        responseObserver.start();
        responseObserver.await();
        log.info("--------------");
    }

    @AfterAll
    void stop() {
        this.server.stop();
    }

}
