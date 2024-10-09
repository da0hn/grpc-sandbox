package dev.da0hn.grpc.section03;

import dev.da0hn.grpc.proto.models.section03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScalarTypes {

  private static final Logger LOGGER = LoggerFactory.getLogger(ScalarTypes.class);

  public static void main(String[] args) {
    /**
     * | Java                     | Proto                     |
     * |--------------------------|---------------------------|
     * | String                   | string                    |
     * | int                      | int32                     |
     * | long                     | int64                     |
     * | float                    | float                     |
     * | double                   | double                    |
     * | boolean                  | bool                      |
     * | enum                     | enum                      |
     * | byte[]                   | bytes                     |
     * | BigDecimal               | string                    |
     * | BigInteger               | string                    |
     * | java.util.UUID           | string                    |
     * | java.time.Instant        | google.protobuf.Timestamp |
     * | java.time.Duration       | google.protobuf.Duration  |
     * | java.time.LocalDate      | google.protobuf.Timestamp |
     * | java.time.LocalTime      | google.protobuf.Timestamp |
     * | java.time.LocalDateTime  | google.protobuf.Timestamp |
     * | java.time.ZonedDateTime  | google.protobuf.Timestamp |
     * | java.time.OffsetDateTime | google.protobuf.Timestamp |
     * | java.time.OffsetTime     | google.protobuf.Timestamp |
     * | java.time.ZoneId         | string                    |
     * | java.time.ZoneOffset     | google.protobuf.Duration  |
     * | java.time.Period         | google.protobuf.Duration  |
     * | java.time.Year           | int32                     |
     * | java.time.Month          | int32                     |
     * | java.time.YearMonth      | google.protobuf.Timestamp |
     * | java.time.MonthDay       | google.protobuf.Timestamp |
     */
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
    LOGGER.info("person -> {}", person);
  }

}
