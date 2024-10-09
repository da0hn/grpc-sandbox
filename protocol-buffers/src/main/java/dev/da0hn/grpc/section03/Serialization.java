package dev.da0hn.grpc.section03;

import dev.da0hn.grpc.proto.models.section03.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Serialization {

  private static final Logger LOGGER = LoggerFactory.getLogger(Serialization.class);
  private static final Path PATH = Path.of("person.out");

  public static void main(String[] args) throws IOException {
    final var person1 = Person.newBuilder()
      .setFirstName("John")
      .setLastName("Doe")
      .setAge(25)
      .setEmail("john.doe@gmail.com")
      .setEmployed(true)
      .setBalance(2_700)
      .setSalary(10_000.0)
      .setBankAccountNumber(138_591_853_891L)
      .build();
    serialize(person1);
    final var person2 = deserialize();
    LOGGER.info("person2 -> {}", person2);
    LOGGER.info("person1.equals(person2) -> {}", person1.equals(person2));
    LOGGER.info("bytes length -> {} bytes", person2.toByteArray().length);
  }

  private static void serialize(Person person) throws IOException {
    LOGGER.info("Serializing the object");
    person.writeTo(Files.newOutputStream(PATH));
  }

  private static Person deserialize() throws IOException {
    LOGGER.info("Deserializing the object");
    return Person.parseFrom(Files.newInputStream(PATH));
  }



}
