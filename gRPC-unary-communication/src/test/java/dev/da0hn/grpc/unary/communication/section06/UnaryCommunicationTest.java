package dev.da0hn.grpc.unary.communication.section06;

import com.google.protobuf.Empty;
import dev.da0hn.grpc.proto.section06.Accounts;
import dev.da0hn.grpc.proto.section06.BalanceCheckRequest;
import dev.da0hn.grpc.unary.communication.section06.repository.AccountRepository;
import io.grpc.stub.StreamObserver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

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
    @DisplayName("Should get all accounts asynchronously")
    void test3() throws InterruptedException {
        final var latch = new CountDownLatch(1);
        this.asyncStub.getAllAccounts(
            Empty.getDefaultInstance(), new StreamObserver<>() {
                @Override
                public void onNext(final Accounts value) {
                    log.info("Received async accounts: {}", value);
                    Assertions.assertThat(value.getAccountsList()).isNotEmpty();
                    Assertions.assertThat(value.getAccountsCount()).isEqualTo(AccountRepository.ACCOUNTS_QUANTITY);
                }

                @Override
                public void onError(final Throwable t) {
                    Assertions.fail(t);
                }

                @Override
                public void onCompleted() {
                    latch.countDown();
                }
            }
        );
        latch.await();
    }

}
