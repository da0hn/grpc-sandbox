package dev.da0hn.grpc.section04;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Timestamp;
import dev.da0hn.grpc.proto.models.section04.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class WellKnownTypes {

    private static final Logger log = LoggerFactory.getLogger(WellKnownTypes.class);

    public static void main(String[] args) {
        final var sample = Sample.newBuilder()
            .setAge(Int32Value.of(12))
            .setLoginTime(Timestamp.newBuilder().setSeconds(Instant.now().getEpochSecond()).build())
            .build();
        log.info("Sample: {}", sample);
        final var convertedLoginTime = Instant.ofEpochSecond(sample.getLoginTime().getSeconds());
    }

}
