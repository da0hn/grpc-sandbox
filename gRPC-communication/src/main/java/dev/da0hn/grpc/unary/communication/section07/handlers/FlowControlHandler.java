package dev.da0hn.grpc.unary.communication.section07.handlers;

import dev.da0hn.grpc.proto.section07.Input;
import dev.da0hn.grpc.proto.section07.Output;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class FlowControlHandler implements StreamObserver<Input> {

    private static final Logger log = LoggerFactory.getLogger(FlowControlHandler.class);

    private final StreamObserver<Output> responseObserver;

    private int emitted;

    public FlowControlHandler(final StreamObserver<Output> responseObserver) { this.responseObserver = responseObserver; }

    @Override
    public void onNext(final Input value) {
        IntStream.rangeClosed(this.emitted + 1, 100)
            .limit(value.getSize())
            .forEach(index -> {
                log.info("Emitting {} of {}", index, value.getSize());
                this.responseObserver.onNext(Output.newBuilder().setValue(index).build());
            });
        this.emitted += value.getSize();
    }

    @Override
    public void onError(final Throwable t) {
        log.error("Error receiving message from client: {}", t.getMessage());
        this.responseObserver.onError(t);
    }

    @Override
    public void onCompleted() {
        log.info("Flow control completed");
        this.responseObserver.onCompleted();
    }

}
