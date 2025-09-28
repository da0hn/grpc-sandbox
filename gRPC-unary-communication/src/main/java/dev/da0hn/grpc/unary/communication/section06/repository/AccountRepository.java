package dev.da0hn.grpc.unary.communication.section06.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountRepository {

    private static final Map<Integer, Integer> IN_MEMORY_DATABASE = IntStream.rangeClosed(1, 1000)
        .boxed()
        .collect(Collectors.toConcurrentMap(Function.identity(), _ -> ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE)));

    private AccountRepository() { }

    public static Integer getAccountBalance(final Integer accountNumber) {
        return IN_MEMORY_DATABASE.get(accountNumber);
    }

    public static Map<Integer, Integer> getAllAccounts() {
        return new HashMap<>(IN_MEMORY_DATABASE);
    }





}
