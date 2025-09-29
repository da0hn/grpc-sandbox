package dev.da0hn.grpc.unary.communication.section06;

import com.google.protobuf.Empty;
import dev.da0hn.grpc.proto.section06.AccountBalance;
import dev.da0hn.grpc.proto.section06.Accounts;
import dev.da0hn.grpc.proto.section06.BalanceCheckRequest;
import dev.da0hn.grpc.unary.communication.section06.repository.AccountRepository;
import dev.da0hn.grpc.unary.communication.shared.ResponseObservable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class UnaryCommunicationTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(UnaryCommunicationTest.class);

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
        final var response = this.blockingStub.getAllAccounts(Empty.newBuilder().build());
        Assertions.assertThat(response.getAccountsList()).isNotEmpty();
        Assertions.assertThat(response.getAccountsCount()).isEqualTo(AccountRepository.ACCOUNTS_QUANTITY);
    }

    @Test
    @DisplayName("Should get account balance asynchronously for a given account number")
    void test3() {
        final var expectedAccountNumber = 123;
        final var request = BalanceCheckRequest.newBuilder()
            .setAccountNumber(expectedAccountNumber)
            .build();
        final var responseObserver = ResponseObservable.<AccountBalance>create();
        this.asyncStub.getAccountBalance(
            request,
            responseObserver
        );
        responseObserver.await();
        final var accountBalance = responseObserver.getValueOrThrow();
        Assertions.assertThat(accountBalance.getAccountNumber()).isEqualTo(expectedAccountNumber);
        Assertions.assertThat(accountBalance.getBalance()).isNotZero();
    }

    @Test
    @DisplayName("Should get all accounts asynchronously")
    void test4() {
        final var responseObserver = ResponseObservable.<Accounts>create();
        this.asyncStub.getAllAccounts(
            Empty.newBuilder().build(),
            responseObserver
        );
        responseObserver.await();
        final var accounts = responseObserver.getValueOrThrow();
        Assertions.assertThat(accounts.getAccountsList()).isNotEmpty();
        Assertions.assertThat(accounts.getAccountsCount()).isEqualTo(AccountRepository.ACCOUNTS_QUANTITY);
    }

}
