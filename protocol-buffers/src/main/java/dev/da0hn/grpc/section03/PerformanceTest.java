package dev.da0hn.grpc.section03;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import dev.da0hn.grpc.proto.models.section03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PerformanceTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceTest.class);

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static void main(String[] args) throws InterruptedException {
    List<Thread> tasks = new ArrayList<>();

    final var protoThreadFactory = Thread.ofVirtual()
      .name("proto-vthread-", 0)
      .factory();
    final var jsonThreadFactory = Thread.ofVirtual()
      .name("json-vthread-", 0)
      .factory();

    for (int i = 0; i < 10; i++) {
      final var protoThread = protoThreadFactory.newThread(() -> runTest("Proto", PerformanceTest::proto));
      final var jsonThread = jsonThreadFactory.newThread(() -> runTest("JSON", PerformanceTest::json));
      tasks.add(protoThread);
      tasks.add(jsonThread);
    }

    tasks.forEach(Thread::start);

    for (Thread task : tasks) {
      task.join();
    }
  }

  private static void proto() {
    final var person = Person.newBuilder()
      .setFirstName("John")
      .setLastName("Doe")
      .setAge(25)
      .setEmail("john.doe@gmail.com")
      .setEmployed(true)
      .setBalance(2_700)
      .setSalary(10_000.0)
      .setBankAccountNumber(138_591_853_891L)
      .build();
    try {
      final var bytes = person.toByteArray();
      Person.parseFrom(bytes);
    }
    catch (InvalidProtocolBufferException e) {
      throw new RuntimeException(e);
    }
  }

  private static void json() {
    final var person = new PersonJson(
      "John",
      "Doe",
      25,
      "john.doe@gmail.com",
      true,
      2_700,
      10_000.0,
      138_591_853_891L
    );
    try {
      var bytes = OBJECT_MAPPER.writeValueAsBytes(person);
      OBJECT_MAPPER.readValue(bytes, PersonJson.class);
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void runTest(String testName, Runnable runnable) {
    final var start = Instant.now();
    for (int i = 0; i < 10_000_000; i++) {
      runnable.run();
    }
    final var end = Instant.now();
    LOGGER.info("{} -> {}ms", testName, ChronoUnit.MILLIS.between(start, end));
  }

  record PersonJson(
    String firstName,
    String lastName,
    Integer age,
    String email,
    Boolean employed,
    Integer balance,
    Double salary,
    Long bankAccountNumber
  ) { }

}
