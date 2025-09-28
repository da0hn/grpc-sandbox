package dev.da0hn.grpc.unary.communication.shared;

import dev.da0hn.grpc.unary.communication.section06.BankService;
import io.grpc.ServerBuilder;

import java.io.IOException;

public final class GrpcServer {

    private GrpcServer() { }

    static void main(final String[] args) throws IOException, InterruptedException {
        final var grpcServer = ServerBuilder.forPort(6565)
            .addService(new BankService())
            .build();
        grpcServer.start();
        grpcServer.awaitTermination();

    }

}
