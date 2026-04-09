package com.example.trendingservice.service;

import com.example.trendingservice.dto.MovieDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoCacheService {

    @Autowired
    private RestTemplate restTemplate;

    @Cacheable(value = "movieInfo", key = "#movieId")
    public MovieDTO getMovieInfo(int movieId) {
        System.out.println("Cache miss — fetching from movie-info-service for movieId: " + movieId);
        try {
            return restTemplate.getForObject(
                    "http://movie-info-service/movies/" + movieId,
                    MovieDTO.class
            );
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            System.out.println("Skipping movieId " + movieId + " — not found");
            return null;
        } catch (Exception e) {
            System.out.println("Error fetching movieId " + movieId + ": " + e.getMessage());
            return null;
        }
    }
}
