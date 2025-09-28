package dev.da0hn.grpc.unary.communication.section06;

import dev.da0hn.grpc.proto.section06.BankServiceGrpc;
import dev.da0hn.grpc.unary.communication.shared.AbstractChannelTest;
import dev.da0hn.grpc.unary.communication.shared.GrpcServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractTest extends AbstractChannelTest {

    private final GrpcServer grpcServer = GrpcServer.create(new BankService());

    protected BankServiceGrpc.BankServiceBlockingStub blockingStub;

    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.blockingStub = BankServiceGrpc.newBlockingStub(this.channel);
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }

}
