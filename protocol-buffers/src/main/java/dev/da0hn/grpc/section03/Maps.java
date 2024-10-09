package dev.da0hn.grpc.section03;

import dev.da0hn.grpc.proto.models.section03.Car;
import dev.da0hn.grpc.proto.models.section03.Dealer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Maps {

  private static final Logger LOGGER = LoggerFactory.getLogger(Maps.class);

  public static void main(final String[] args) {
    final var car1 = Car.newBuilder()
      .setModel("Toyota")
      .setMake("Corolla")
      .setYear(2021)
      .build();
    final var car2 = Car.newBuilder()
      .setModel("Toyota")
      .setMake("Camry")
      .setYear(2021)
      .build();
    final var dealer = Dealer.newBuilder()
      .setName("John")
      .putInventory(1, car1)
      .putInventory(2, car2)
      .build();
    LOGGER.info("dealer -> {}", dealer);
  }

}
