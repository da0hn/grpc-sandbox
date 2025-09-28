package dev.da0hn.grpc.unary.communication.section06;

import dev.da0hn.grpc.proto.section06.AccountBalance;
import dev.da0hn.grpc.proto.section06.BalanceCheckRequest;
import dev.da0hn.grpc.proto.section06.BankServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getAccountBalance(final BalanceCheckRequest request, final StreamObserver<AccountBalance> responseObserver) {
        final var accountBalance = AccountBalance.newBuilder()
            .setAccountNumber(request.getAccountNumber())
            .setBalance(request.getAccountNumber() * ThreadLocalRandom.current().nextInt(1, 25))
            .build();
        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }

}
