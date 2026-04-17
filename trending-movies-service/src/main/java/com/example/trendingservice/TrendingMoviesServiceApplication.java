package com.example.trendingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
@EnableEurekaClient


@EnableJpaRepositories(basePackages = "com.example.trendingservice.repositories.jpa")
@EnableMongoRepositories(basePackages = "com.example.trendingservice.repositories.mongo")
public class TrendingMoviesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrendingMoviesServiceApplication.class, args);
    }

}

