package dev.da0hn.grpc.section02;

import dev.da0hn.grpc.proto.models.section02.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Section02Demonstration {

  private static final Logger LOGGER = LoggerFactory.getLogger(Section02Demonstration.class);

  public static void main(String[] args) {
    // create new person instance
    final var person1 = createPerson();
    // create another instance with same values
    final var person2 = createPerson();
    // comparison
    LOGGER.info("person1.equals(person2) -> {}", person1.equals(person2));
    LOGGER.info("person1 == person2 -> {}", (person1 == person2));

    // Mutable? Nop
    // create new instance with different name using .toBuilder()
    final var person3 = person1.toBuilder()
      .setName("Jane Doe")
      .build();
    // compare
    LOGGER.info("person1.equals(person3) -> {}", person1.equals(person3));
    LOGGER.info("person1 == person3 -> {}", (person1 == person3));

    // null? -> Gets NPR
    // final var person4 = person1.toBuilder().setName(null).build();
    final var person4 = person1.toBuilder().clearName().build();
    LOGGER.info("person4 -> {}", person4);

  }

  private static Person createPerson() {
    return Person.newBuilder()
      .setName("John Doe")
      .setAge(25)
      .build();
  }

}
