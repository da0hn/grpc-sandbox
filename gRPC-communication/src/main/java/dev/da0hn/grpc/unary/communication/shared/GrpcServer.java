package dev.da0hn.grpc.unary.communication.shared;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.ServiceDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public final class GrpcServer {

    private static final int DEFAULT_PORT = 6565;

    private static final Logger log = LoggerFactory.getLogger(GrpcServer.class);

    private final Server server;

    private GrpcServer(final Server server) { this.server = server; }

    public static GrpcServer create(final int port, final BindableService... services) {
        final var server = ServerBuilder.forPort(port);
        Arrays.stream(services).forEach(server::addService);
        return new GrpcServer(server.build());
    }

    public static GrpcServer create(final BindableService... services) {
        return create(DEFAULT_PORT, services);
    }

    public GrpcServer start() {
        try {
            this.server.start();
            final var servicesRegistered = this.server.getServices().stream()
                .map(ServerServiceDefinition::getServiceDescriptor)
                .map(ServiceDescriptor::getName)
                .toList();
            log.info("Server started, listening on {}", this.server.getPort());
            log.info("Services registered: {}", servicesRegistered);
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public void await() {
        try {
            this.server.awaitTermination();
        }
        catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        this.server.shutdownNow();
        log.info("Stopping server...");
    }

}
