package dev.da0hn.grpc.unary.communication.shared;

import dev.da0hn.grpc.unary.communication.section06.BankService;

public class Entrypoint {

    static void main(final String[] args) {
        GrpcServer.create(new BankService())
            .start()
            .await();
    }

}
