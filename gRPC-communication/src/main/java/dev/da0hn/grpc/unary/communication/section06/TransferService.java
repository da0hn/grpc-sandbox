package dev.da0hn.grpc.unary.communication.section06;

import dev.da0hn.grpc.proto.section06.TransferRequest;
import dev.da0hn.grpc.proto.section06.TransferResponse;
import dev.da0hn.grpc.proto.section06.TransferServiceGrpc;
import dev.da0hn.grpc.unary.communication.section06.handlers.TransferRequestHandler;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(TransferService.class);

    @Override
    public StreamObserver<TransferRequest> transfer(final StreamObserver<TransferResponse> responseObserver) {
        return new TransferRequestHandler(responseObserver);
    }

}
