package com.in28munites.microservices.apigateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
	@Autowired
	JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p.path("/currency-exchange/**").filters(f -> f.filter(filter))
						.uri("lb://currency-exchange"))
				.route(p -> p.path("/currency-conversion/**").filters(f -> f.filter(filter))
						.uri("lb://currency-conversion"))
				.route(p -> p.path("/auth/**").filters(f -> f.filter(filter)).uri("lb://iam")).build();
	}
}
