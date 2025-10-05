package dev.da0hn.grpc.unary.communication.section06.repository;

import dev.da0hn.grpc.proto.section06.Money;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountRepository {

    public static final int ACCOUNTS_QUANTITY = 10;

    private static final Map<Integer, Integer> IN_MEMORY_DATABASE = IntStream.rangeClosed(1, ACCOUNTS_QUANTITY)
        .boxed()
        .collect(Collectors.toConcurrentMap(Function.identity(), _ -> ThreadLocalRandom.current().nextInt(1, 10_000)));

    private AccountRepository() { }

    public static Integer getAccountBalance(final Integer accountNumber) {
        return IN_MEMORY_DATABASE.getOrDefault(accountNumber, null);
    }

    public static void deductAmount(final int accountNumber, final int amount) {
        IN_MEMORY_DATABASE.computeIfPresent(accountNumber, (key, value) -> value - amount);
    }

    public static Map<Integer, Integer> getAllAccounts() {
        return new HashMap<>(IN_MEMORY_DATABASE);
    }

    public static void deposit(final int accountNumber, final int amount) {
        IN_MEMORY_DATABASE.computeIfPresent(accountNumber,(key, value) -> value + amount);
    }

}
