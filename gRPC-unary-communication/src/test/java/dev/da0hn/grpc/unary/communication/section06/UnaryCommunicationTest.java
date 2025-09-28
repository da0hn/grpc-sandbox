package dev.da0hn.grpc.unary.communication.section06;

import dev.da0hn.grpc.proto.section06.BalanceCheckRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UnaryCommunicationTest extends AbstractTest {

    @Test
    @DisplayName("")
    void test1() {
        final var expectedAccountNumber = 123;
        final var request = BalanceCheckRequest.newBuilder()
            .setAccountNumber(expectedAccountNumber)
            .build();
        final var response = this.blockingStub.getAccountBalance(request);
        Assertions.assertThat(response.getBalance()).isNotZero();
        Assertions.assertThat(response.getAccountNumber()).isEqualTo(expectedAccountNumber);

    }

}
