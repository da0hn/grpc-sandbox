package dev.da0hn.grpc.unary.communication.section07;

import dev.da0hn.grpc.proto.section07.FlowControlServiceGrpc;
import dev.da0hn.grpc.proto.section07.Input;
import dev.da0hn.grpc.proto.section07.Output;
import dev.da0hn.grpc.unary.communication.section07.handlers.FlowControlHandler;
import io.grpc.stub.StreamObserver;

public class FlowControlService extends FlowControlServiceGrpc.FlowControlServiceImplBase {

    @Override
    public StreamObserver<Input> getMessages(final StreamObserver<Output> responseObserver) {
        return new FlowControlHandler(responseObserver);
    }

}
