package com.example.correlationidlogspringwebflux;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class CorrelationIdLogSpringWebfluxApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void baseTest() {
        webTestClient.get()
                .uri("/baseHello")
                .header("requestId", "111111111111111111111111")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(actual -> {
                    Assertions.assertThat(actual).isEqualTo("hello");
                });
    }

    @Test
    void loss_correlationID() {
        webTestClient.get()
                .uri("/hello")
                .header("requestId", "111111111111111111111111")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(actual -> {
                    Assertions.assertThat(actual).isEqualTo("hello fromboundedElastic-1");
                });
    }

    @Test
    void propagation_correlationID() {
        webTestClient.get()
                .uri("/helloCtx")
                .header("requestId", "111111111111111111111111")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(actual -> {
                    Assertions.assertThat(actual).isEqualTo("hello fromboundedElastic-1");
                });
    }

    @Test
    void propagation_correlationID_auto() {
        webTestClient.get()
                .uri("/propagationHelloCtx")
                .header("requestId", "111111111111111111111111")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(actual -> {
                    Assertions.assertThat(actual).isEqualTo("hello fromboundedElastic-1");
                });
    }

}
