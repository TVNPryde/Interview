package com.fyllo.interview.service;

import com.fyllo.interview.common.PersistentQueue;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class WebClientService {

    private final AtomicInteger COUNTER = new AtomicInteger(0);

    @Autowired
    private WebClient webClient;

    @Autowired
    private RateLimiter rateLimiter;

    private PersistentQueue queue = PersistentQueue.init();

    @Async
    public void doAsyncCall() {
        queue.increment();
        send().subscribe();
    }

    public Mono<String> send() {
        return webClient.post()
                .uri("/post")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSubscribe(s -> queue.decrement())
                .doOnSuccess(s -> {
                    // This block is for checking the state of the app.
                    System.out.println(String.format("Sending #%d at %s. Queue remaining: %d",
                            COUNTER.incrementAndGet(), Instant.now(), queue.getQueue()));
                })
                .transformDeferred(RateLimiterOperator.of(rateLimiter));
    }
}
