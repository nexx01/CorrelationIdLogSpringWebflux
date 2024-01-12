package com.example.correlationidlogspringwebflux;

import io.micrometer.context.ContextRegistry;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class CorrelationIdLogSpringWebfluxApplication {

    public static void main(String[] args) {

        //2 //3
//        Hooks.enableAutomaticContextPropagation();
        //2
//        ContextRegistry.getInstance()
//                .registerThreadLocalAccessor("requestId",
//                        () -> MDC.get("requestId"),
//                        requestId -> MDC.put("requestId", requestId),
//                        () -> MDC.remove("requestId"));

        SpringApplication.run(CorrelationIdLogSpringWebfluxApplication.class, args);
    }

}
