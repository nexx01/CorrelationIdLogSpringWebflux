package com.example.correlationidlogspringwebflux;

import org.slf4j.MDC;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Component
public class MyWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        System.out.println("----------------------------------------------------------------");
        System.out.println("Filter done");
        System.out.println("----------------------------------------------------------------");
        var request =  exchange.getRequest();
        var requestId = request.getHeaders().get("requestId").get(0);
        MDC.put("requestId", requestId);

        return chain.filter(exchange);
    }

//    //2
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//
//        System.out.println("----------------------------------------------------------------");
//        System.out.println("Filter done");
//        System.out.println("----------------------------------------------------------------");
//        var request =  exchange.getRequest();
//        var requestId = request.getHeaders().get("requestId").get(0);
////        MDC.put("requestId", requestId);  // Это больше не нужно
//
//        return chain.filter(exchange)
//                // Реактивный контекст является источником правды
//                // Для распространения нашего пользовательского интерфейса
//                // ThreadLocal нам необходимо зарегистрировать ThreadLocalContextAccessor:
//                //    и регистрацию хука авто распостранения контекста
//                //     (example: CorrelationIdLogSpringWebfluxApplication)
//                //        Hooks.enableAutomaticContextPropagation();
//                //
//                //        ContextRegistry.getInstance()
//                //                .registerThreadLocalAccessor("requestId",
//                //                        () -> MDC.get("requestId"),
//                //                        requestId -> MDC.put("requestId", requestId),
//                //                        () -> MDC.remove("requestId"))
//                //https://spring.io/blog/2023/03/30/context-propagation-with-project-reactor-3-unified-bridging-between-reactive/
//                .contextWrite(Context.of("requestId", requestId))
//                ;
//    }
}
