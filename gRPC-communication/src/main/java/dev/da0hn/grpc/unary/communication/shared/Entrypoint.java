package dev.da0hn.grpc.unary.communication.shared;

import dev.da0hn.grpc.unary.communication.section06.BankService;
import dev.da0hn.grpc.unary.communication.section06.TransferService;

public class Entrypoint {

    static void main(final String[] args) {
        GrpcServer.create(new BankService(), new TransferService())
            .start()
            .await();
    }

}
