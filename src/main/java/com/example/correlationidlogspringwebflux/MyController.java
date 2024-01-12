package com.example.correlationidlogspringwebflux;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

@RestController
@RequestMapping
@Slf4j
public class MyController {

    @GetMapping("/baseHello")
    public Mono<String> baseHello() {
        log.info("Hello! from begin MyController in thread: {}",Thread.currentThread().getName());
        return Mono.just("hello");
    }

    @GetMapping("/hello")
    public Mono<String> hello() {
        log.info("Hello! from begin MyController in thread: {}",Thread.currentThread().getName());
        return Mono.just("hello")
                .publishOn(Schedulers.boundedElastic())
                .map(str->{
                    log.info("hello logged in thread: {}", Thread.currentThread().getName());
                    System.out.println("----------------------------------------------------------------");
                    return str.concat(" from"+Thread.currentThread().getName());
                });
    }

    @GetMapping("/helloCtx")
    public Mono<String> helloCtx() {
        log.info("Hello! from begin MyController in thread: {}",Thread.currentThread().getName());
        return Mono.deferContextual(ctx->Mono.just("hello")
                        .publishOn(Schedulers.boundedElastic())
                        .map(str -> {
                            MDC.put("requestId",ctx.get("requestId"));
                            log.info("hello logged in thread: {}", Thread.currentThread().getName());
                            System.out.println("----------------------------------------------------------------");
                            return str.concat(" from" + Thread.currentThread().getName());
                        }))
                .contextWrite(f -> Context.of(MDC.getCopyOfContextMap()));
    }

    @GetMapping("/propagationHelloCtx")
    public Mono<String> propagationHelloCtx() {
        log.info("Hello! from begin MyController in thread: {}",Thread.currentThread().getName());
        return Mono.just("hello")
                .publishOn(Schedulers.boundedElastic())
                .map(str -> {
                    log.info("hello logged in thread: {}", Thread.currentThread().getName());
                    System.out.println("----------------------------------------------------------------");
                    return str.concat(" from" + Thread.currentThread().getName());
                });

    }

}
