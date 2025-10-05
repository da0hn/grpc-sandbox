package dev.da0hn.grpc.unary.communication.section06.handlers;

import dev.da0hn.grpc.proto.section06.AccountBalance;
import dev.da0hn.grpc.proto.section06.TransferRequest;
import dev.da0hn.grpc.proto.section06.TransferResponse;
import dev.da0hn.grpc.proto.section06.TransferStatus;
import dev.da0hn.grpc.unary.communication.section06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferRequestHandler implements StreamObserver<TransferRequest> {

    private static final Logger log = LoggerFactory.getLogger(TransferRequestHandler.class);

    private final StreamObserver<TransferResponse> responseObserver;

    public TransferRequestHandler(final StreamObserver<TransferResponse> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(final TransferRequest request) {
        log.info("Received transfer of amount {} from account {} to account {}", request.getAmount(), request.getFromAccount(), request.getToAccount());
        final var fromAccountBalance = AccountRepository.getAccountBalance(request.getFromAccount());
        if (request.getAmount() > fromAccountBalance || request.getFromAccount() == request.getToAccount()) {
            log.warn("Transfer request rejected due to insufficient funds or same account transfer.");
            this.responseObserver.onNext(
                TransferResponse.newBuilder()
                    .setStatus(TransferStatus.REJECTED)
                    .setFromAccount(this.buildAccountBalance(request.getFromAccount()))
                    .setToAccount(this.buildAccountBalance(request.getToAccount()))
                    .build()
            );
            return;
        }
        AccountRepository.deductAmount(request.getFromAccount(), request.getAmount());
        AccountRepository.deposit(request.getToAccount(), request.getAmount());
        log.info("Transfer request between account {} and {} accepted.", request.getFromAccount(), request.getToAccount());
        this.responseObserver.onNext(
            TransferResponse.newBuilder()
                .setStatus(TransferStatus.COMPLETED)
                .setFromAccount(this.buildAccountBalance(request.getFromAccount()))
                .setToAccount(this.buildAccountBalance(request.getToAccount()))
                .build()
        );
    }

    @Override
    public void onError(final Throwable throwable) {
        log.info("Client error: {}", throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        this.responseObserver.onCompleted();
    }

    private AccountBalance buildAccountBalance(final int account) {
        return AccountBalance.newBuilder()
            .setAccountNumber(account)
            .setBalance(AccountRepository.getAccountBalance(account))
            .build();
    }

}
