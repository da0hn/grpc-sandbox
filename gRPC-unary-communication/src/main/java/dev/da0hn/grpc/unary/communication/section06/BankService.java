package dev.da0hn.grpc.unary.communication.section06;

import com.google.protobuf.Empty;
import dev.da0hn.grpc.proto.section06.AccountBalance;
import dev.da0hn.grpc.proto.section06.Accounts;
import dev.da0hn.grpc.proto.section06.BalanceCheckRequest;
import dev.da0hn.grpc.proto.section06.BankServiceGrpc;
import dev.da0hn.grpc.unary.communication.section06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getAccountBalance(final BalanceCheckRequest request, final StreamObserver<AccountBalance> responseObserver) {

        final var balance = AccountRepository.getAccountBalance(request.getAccountNumber());

        final var accountBalance = AccountBalance.newBuilder()
            .setAccountNumber(request.getAccountNumber())
            .setBalance(balance)
            .build();

        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAccountsStream(final Empty request, final StreamObserver<AccountBalance> responseObserver) {

        final var accounts = AccountRepository.getAllAccounts();

        for (final var account : accounts.entrySet()) {
            final var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(account.getKey())
                .setBalance(account.getValue())
                .build();
            responseObserver.onNext(accountBalance);
            sleep(ThreadLocalRandom.current().nextInt(100, 2_500));
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAccounts(final Empty request, final StreamObserver<Accounts> responseObserver) {
        final var accounts = AccountRepository.getAllAccounts();

        final var accountsResponse = Accounts.newBuilder();

        accounts.entrySet()
            .stream()
            .map(entry -> AccountBalance.newBuilder()
                .setAccountNumber(entry.getKey())
                .setBalance(entry.getValue())
                .build())
            .forEach(accountsResponse::addAccounts);

        responseObserver.onNext(accountsResponse.build());
        responseObserver.onCompleted();
    }

    private static void sleep(final int timeoutInMs) {
        try {
            TimeUnit.MILLISECONDS.sleep(timeoutInMs);
        }
        catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
