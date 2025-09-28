package dev.da0hn.grpc.unary.communication.section06;

import dev.da0hn.grpc.unary.communication.shared.GrpcServer;

public class Demonstration {

    static void main(final String[] args) {
        GrpcServer.create(new BankService())
            .start()
            .await();
    }

}
