package dev.da0hn.grpc.section05.parser;

import com.google.protobuf.InvalidProtocolBufferException;
import dev.da0hn.grpc.proto.models.section05.v1.Television;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class V1Parser {

    private static final Logger log = LoggerFactory.getLogger(V1Parser.class);

    public static void parse(final byte[] bytes) throws InvalidProtocolBufferException {
        log.info("Parsing with V1Parser...");
        final var tv = Television.parseFrom(bytes);
        log.info("Brand: {}", tv.getBrand());
        log.info("Year: {}", tv.getYear());
    }

}
