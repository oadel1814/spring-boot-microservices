package com.example.trendingservice.controller;


import com.example.trendingservice.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class TestController {

    @Autowired
    private MoviesService moviesService;

    @GetMapping
    ResponseEntity<?> getTopMovies(){
       return ResponseEntity.ok().body(moviesService.getMovies(5,"trending"));
    }
}
