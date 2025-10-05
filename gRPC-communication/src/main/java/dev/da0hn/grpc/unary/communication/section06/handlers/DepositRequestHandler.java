package dev.da0hn.grpc.unary.communication.section06.handlers;

import dev.da0hn.grpc.proto.section06.AccountBalance;
import dev.da0hn.grpc.proto.section06.DepositRequest;
import dev.da0hn.grpc.unary.communication.section06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepositRequestHandler implements StreamObserver<DepositRequest> {

    private static final Logger log = LoggerFactory.getLogger(DepositRequestHandler.class);

    private final StreamObserver<AccountBalance> responseObserver;

    private int accountNumber;

    public DepositRequestHandler(final StreamObserver<AccountBalance> responseObserver) { this.responseObserver = responseObserver; }

    @Override
    public void onNext(final DepositRequest request) {
        switch (request.getRequestCase()) {
            case ACCOUNT_NUMBER -> {
                log.info("[ACCOUNT_NUMBER] received request to deposit amount. Account number: {}", request.getAccountNumber());
                this.accountNumber = request.getAccountNumber();
            }
            case MONEY -> {
                log.info(
                    "[MONEY] received request to deposit amount. Account number: {}, amount: {}",
                    this.accountNumber,
                    request.getMoney().getValue()
                );
                AccountRepository.deposit(this.accountNumber, request.getMoney().getValue());
            }
            case REQUEST_NOT_SET -> throw new RuntimeException("[REQUEST_NOT_SET] Invalid request");
        }
    }

    @Override
    public void onError(final Throwable t) {
        log.info("Client error: {}", t.getMessage());
    }

    @Override
    public void onCompleted() {
        final var accountBalance = AccountBalance.newBuilder()
            .setBalance(AccountRepository.getAccountBalance(this.accountNumber))
            .setAccountNumber(this.accountNumber)
            .build();

        this.responseObserver.onNext(accountBalance);
        this.responseObserver.onCompleted();
    }

}
