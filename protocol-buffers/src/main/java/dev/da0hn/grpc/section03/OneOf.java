package dev.da0hn.grpc.section03;

import dev.da0hn.grpc.proto.models.section03.Credentials;
import dev.da0hn.grpc.proto.models.section03.Email;
import dev.da0hn.grpc.proto.models.section03.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OneOf {

    private static final Logger LOGGER = LoggerFactory.getLogger(OneOf.class);

    private OneOf() { }

    public static void main(final String[] args) {
        final var email = Email.newBuilder()
            .setAddress("gabriel@gmail.com")
            .setPassword("G@bR1el")
            .build();
        final var phone = Phone.newBuilder()
            .setNumber(9999000)
            .setCode(00)
            .build();
        handleCredentials(Credentials.newBuilder().setEmail(email).build());
        handleCredentials(Credentials.newBuilder().setPhone(phone).build());

        /*
         * O email foi adicionado por último então a instância de Credentials considera apenas o email
         */
        handleCredentials(Credentials.newBuilder().setPhone(phone).setEmail(email).build());
    }

    private static void handleCredentials(final Credentials credentials) {
        switch (credentials.getLoginTypeCase()) {
            case EMAIL -> LOGGER.info("Logging in with email: {}", credentials.getEmail());
            case PHONE -> LOGGER.info("Logging in with username: {}", credentials.getPhone());
            case LOGINTYPE_NOT_SET -> LOGGER.warn("Login type not set");
        }
    }

}
