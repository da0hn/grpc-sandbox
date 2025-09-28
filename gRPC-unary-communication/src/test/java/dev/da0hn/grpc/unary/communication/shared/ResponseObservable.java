package dev.da0hn.grpc.unary.communication.shared;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ResponseObservable<T> implements StreamObserver<T> {

    private static final Logger log = LoggerFactory.getLogger(ResponseObservable.class);

    private final List<T> list = Collections.synchronizedList(new ArrayList<>());

    private final CountDownLatch countDownLatch;

    private Throwable throwable;

    private ResponseObservable(final CountDownLatch countDownLatch) { this.countDownLatch = countDownLatch; }

    public static <T> ResponseObservable<T> create() {
        return new ResponseObservable<>(new CountDownLatch(1));
    }

    @Override
    public void onNext(final T value) {
        log.info("Received async item: {}", value);
        this.list.add(value);
    }

    @Override
    public void onError(final Throwable t) {
        log.error("Error received async item: {}", t.getMessage(), t);
        this.throwable = t;
        this.countDownLatch.countDown();
    }

    @Override
    public void onCompleted() {
        log.info("Completed async action");
        this.countDownLatch.countDown();
    }

    public void await() {
        try {
            this.countDownLatch.await(5, TimeUnit.SECONDS);
        }
        catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public List<T> getValues() {
        return new ArrayList<>(this.list);
    }

    public Optional<T> getValue() {
        if (this.list.isEmpty()) {
            return Optional.empty();
        }
        if (this.list.size() > 1) {
            throw new IllegalStateException("More than one value received");
        }
        return Optional.of(this.list.getFirst());
    }

}
