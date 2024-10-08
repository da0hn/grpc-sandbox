package dev.da0hn.grpc;

import dev.da0hn.grpc.proto.models.PersonOuterClass;

public class Main {

  public static void main(String[] args) {
    PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder()
      .setName("John Doe")
      .setAge(21)
      .build();
  }

}
