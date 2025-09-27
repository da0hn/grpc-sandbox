package dev.da0hn.grpc.section05;

import com.google.protobuf.InvalidProtocolBufferException;
import dev.da0hn.grpc.proto.models.section05.v3.Television;
import dev.da0hn.grpc.proto.models.section05.v3.Type;
import dev.da0hn.grpc.section05.parser.V1Parser;
import dev.da0hn.grpc.section05.parser.V2Parser;
import dev.da0hn.grpc.section05.parser.V3Parser;

public class V3VersionCompatibility {

    public static void main(final String[] args) throws InvalidProtocolBufferException {
        final var tv = Television.newBuilder()
            .setBrand("LG")
            .setType(Type.UHD)
            .build();

        V1Parser.parse(tv.toByteArray());
        V2Parser.parse(tv.toByteArray());
        V3Parser.parse(tv.toByteArray());
    }

}
