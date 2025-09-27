package dev.da0hn.grpc.section04;

import dev.da0hn.grpc.proto.models.common.Address;
import dev.da0hn.grpc.proto.models.common.BodyStyle;
import dev.da0hn.grpc.proto.models.common.Car;
import dev.da0hn.grpc.proto.models.section04.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SharedProto {

    private static final Logger log = LoggerFactory.getLogger(SharedProto.class);

    public static void main(String[] args) {

        final var address = Address.newBuilder()
            .setStreet("123 Main St")
            .setCity("Anytown")
            .setState("CA")
            .build();

        final var car = Car.newBuilder()
            .setMake("Toyota")
            .setModel("Camry")
            .setYear(2020)
            .setBodyStyle(BodyStyle.SEDAN)
            .build();

        final var johnDoe = Person.newBuilder()
            .setAddress(address)
            .setCar(car)
            .setName("John Doe")
            .setAge(30)
            .build();

        log.info("John Doe is " + johnDoe.getName());

    }

}
