package dev.da0hn.grpc.unary.communication.shared;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractChannelTest {

    private static final int DEFAULT_PORT = 6565;

    protected ManagedChannel channel;

    @BeforeAll
    public void setupChannel() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", DEFAULT_PORT)
            .usePlaintext()
            .build();
    }

    @AfterAll
    public void stopChannel() throws InterruptedException {
        this.channel.shutdownNow()
            .awaitTermination(5, TimeUnit.SECONDS);
    }

}
