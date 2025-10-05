package dev.da0hn.grpc.unary.communication.section06;

import dev.da0hn.grpc.proto.section06.AccountBalance;
import dev.da0hn.grpc.proto.section06.DepositRequest;
import dev.da0hn.grpc.proto.section06.Money;
import dev.da0hn.grpc.proto.section06.TransferRequest;
import dev.da0hn.grpc.proto.section06.TransferResponse;
import dev.da0hn.grpc.proto.section06.TransferStatus;
import dev.da0hn.grpc.proto.section06.WithdrawRequest;
import dev.da0hn.grpc.unary.communication.section06.repository.AccountRepository;
import dev.da0hn.grpc.unary.communication.shared.ResponseObservable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

public class StreamingCommunicationTest extends AbstractTest {

    @Test
    @DisplayName("Should do withdraw of amount and stream chunks of ten")
    void test1() {
        final var responseObserver = ResponseObservable.<Money>create();
        final var request = WithdrawRequest.newBuilder()
            .setAccountNumber(1)
            .setAmount(50)
            .build();
        this.asyncStub.withdraw(
            request,
            responseObserver
        );

        responseObserver.await();

        final var streamedMoney = responseObserver.getValues();
        Assertions.assertThat(streamedMoney).isNotEmpty();
        Assertions.assertThat(streamedMoney).hasSize(5);
        Assertions.assertThat(streamedMoney)
            .map(Money::getValue)
            .allMatch(rawValue -> rawValue.equals(10));

    }

    @Test
    @DisplayName("Should deposit a streamed amount after provide the account number")
    void test2() {
        final var responseObserver = ResponseObservable.<AccountBalance>create();

        final var requestObserver = this.asyncStub.deposit(responseObserver);

        requestObserver.onNext(DepositRequest.newBuilder()
                                   .setAccountNumber(2)
                                   .build());

        IntStream.rangeClosed(1, 5)
            .forEach(i -> requestObserver.onNext(DepositRequest.newBuilder()
                                                     .setMoney(Money.newBuilder().setValue(10).build())
                                                     .build())
            );

        requestObserver.onCompleted();

        responseObserver.await();

        final var accountBalance = responseObserver.getValues();
        Assertions.assertThat(accountBalance).isNotEmpty();
        Assertions.assertThat(accountBalance).hasSize(1);
        Assertions.assertThat(accountBalance.get(0).getBalance()).isEqualTo(AccountRepository.getAccountBalance(2));

    }

    @Test
    @DisplayName("Should transfer amount between two accounts")
    void test3() {

        final var responseObserver = ResponseObservable.<TransferResponse>create();
        final var requestObserver = this.asyncTransferStub.transfer(responseObserver);

        IntStream.rangeClosed(1, 5)
            .forEach(_ -> requestObserver.onNext(
                TransferRequest.newBuilder()
                    .setFromAccount(1)
                    .setToAccount(2)
                    .setAmount(10)
                    .build())
            );
        requestObserver.onCompleted();
        responseObserver.await();

        Assertions.assertThat(responseObserver.getValues()).isNotEmpty();
        Assertions.assertThat(responseObserver.getValues()).hasSize(5);
        Assertions.assertThat(responseObserver.getValues()).map(TransferResponse::getStatus)
                .allMatch(status -> status.equals(TransferStatus.COMPLETED));
        Assertions.assertThat(responseObserver.getThrowable()).isNull();
    }

}
