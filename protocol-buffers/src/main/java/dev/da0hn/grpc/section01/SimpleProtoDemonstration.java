package dev.da0hn.grpc.section01;

import dev.da0hn.grpc.proto.models.section01.PersonOuterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleProtoDemonstration {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleProtoDemonstration.class);

  public static void main(String[] args) {

    final var johnDoe = PersonOuterClass.Person.newBuilder()
      .setName("John Doe")
      .setAge(21)
      .build();


    LOGGER.info("{}", johnDoe);

  }

}
