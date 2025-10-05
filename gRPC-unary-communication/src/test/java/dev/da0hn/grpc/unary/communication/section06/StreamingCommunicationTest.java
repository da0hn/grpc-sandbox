package dev.da0hn.grpc.unary.communication.section06;

import dev.da0hn.grpc.proto.section06.Money;
import dev.da0hn.grpc.proto.section06.WithdrawRequest;
import dev.da0hn.grpc.unary.communication.shared.ResponseObservable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}
