package dev.da0hn.grpc.unary.communication.section06;

import dev.da0hn.grpc.proto.section06.BalanceCheckRequest;
import dev.da0hn.grpc.unary.communication.section06.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnaryCommunicationTest extends AbstractTest {

    @Test
    @DisplayName("Should get the account balance for a given account number")
    void test1() {
        final var expectedAccountNumber = 123;
        final var request = BalanceCheckRequest.newBuilder()
            .setAccountNumber(expectedAccountNumber)
            .build();
        final var response = this.blockingStub.getAccountBalance(request);
        Assertions.assertThat(response.getBalance()).isNotZero();
        Assertions.assertThat(response.getAccountNumber()).isEqualTo(expectedAccountNumber);
    }

    @Test
    @DisplayName("Should get all accounts")
    void test2() {
        final var response = this.blockingStub.getAllAccounts(com.google.protobuf.Empty.newBuilder().build());
        Assertions.assertThat(response.getAccountsList()).isNotEmpty();
        Assertions.assertThat(response.getAccountsCount()).isEqualTo(AccountRepository.ACCOUNTS_QUANTITY);
    }

}
