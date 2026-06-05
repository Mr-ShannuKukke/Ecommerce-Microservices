package com.ecommerce.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.servlet.function.RouterFunction;
//import org.springframework.web.servlet.function.ServerResponse;
//
//import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
//import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

//	@Bean
//	public RouterFunction<ServerResponse> gatewayRoutes(){
//		return route()
//				.GET("/api/users/**", http("lb://user-service"))
//				.POST("/api/users/**", http("lb://user-service"))
//				.PUT("/api/users/**",http("lb://user-service"))
//				.DELETE("/api/users/**", http("lb://user-service"))
//
//				.GET("/api/products/**", http("lb://product-service"))
//				.POST("/api/products/**", http("lb://product-service"))
//				.PUT("/api/products/**", http("lb://product-service"))
//				.DELETE("/api/products/**", http("lb://product-service"))
//
//				.GET("/api/orders/**", http("lb://order-service"))
//				.POST("/api/orders/**", http("lb://order-service"))
//				.PUT("/api/orders/**", http("lb://order-service"))
//				.DELETE("/api/orders/**", http("lb://order-service"))
//
//				.build();
//	}
}

