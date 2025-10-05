package dev.da0hn.grpc.unary.communication.section06;

import com.google.common.util.concurrent.Uninterruptibles;
import com.google.protobuf.Empty;
import dev.da0hn.grpc.proto.section06.AccountBalance;
import dev.da0hn.grpc.proto.section06.Accounts;
import dev.da0hn.grpc.proto.section06.BalanceCheckRequest;
import dev.da0hn.grpc.proto.section06.BankServiceGrpc;
import dev.da0hn.grpc.proto.section06.DepositRequest;
import dev.da0hn.grpc.proto.section06.Money;
import dev.da0hn.grpc.proto.section06.WithdrawRequest;
import dev.da0hn.grpc.unary.communication.section06.handlers.DepositRequestHandler;
import dev.da0hn.grpc.unary.communication.section06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BankService.class);

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

    @Override
    public void withdraw(final WithdrawRequest request, final StreamObserver<Money> responseObserver) {
        final var account = request.getAccountNumber();
        final var requestedAmount = request.getAmount();
        final var accountBalance = AccountRepository.getAccountBalance(account);
        if (requestedAmount > accountBalance) {
            responseObserver.onCompleted();
            return;
        }

        log.info("Current balance for account {}: {}", account, accountBalance);

        int currentAmountSent = 0;
        for (int i = 0; i < (requestedAmount / 10); i++) {
            currentAmountSent += 10;
            final var money = Money.newBuilder()
                .setValue(10)
                .build();
            responseObserver.onNext(money);
            AccountRepository.deductAmount(account, 10);
            log.info("Sending money to account {}: ({}/{})", account, currentAmountSent, requestedAmount);
            log.info("Current balance for account {}: {}", account, AccountRepository.getAccountBalance(account));
            Uninterruptibles.sleepUninterruptibly(ThreadLocalRandom.current().nextInt(100, 2_500), TimeUnit.MILLISECONDS);
        }
        responseObserver.onCompleted();

    }

    @Override
    public StreamObserver<DepositRequest> deposit(final StreamObserver<AccountBalance> responseObserver) {
        return new DepositRequestHandler(responseObserver);
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
