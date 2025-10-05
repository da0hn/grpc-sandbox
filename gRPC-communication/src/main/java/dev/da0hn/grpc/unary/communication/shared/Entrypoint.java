package dev.da0hn.grpc.unary.communication.shared;

import dev.da0hn.grpc.unary.communication.section06.BankService;
import dev.da0hn.grpc.unary.communication.section06.TransferService;
import dev.da0hn.grpc.unary.communication.section07.FlowControlService;

public class Entrypoint {

    static void main(final String[] args) {
        GrpcServer.create(new BankService(), new TransferService(), new FlowControlService())
            .start()
            .await();
    }

}
