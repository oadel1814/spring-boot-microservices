package com.example.movieinfoservice.resources;

import com.example.movieinfoservice.models.Movie;
import com.example.movieinfoservice.models.MovieCache;
import com.example.movieinfoservice.models.MovieSummary;
import com.example.movieinfoservice.repositories.MovieCacheRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @Value("${api.key}")
    private String apiKey;

    private RestTemplate restTemplate;
    private MovieCacheRepository cacheRepository;

    public MovieResource(RestTemplate restTemplate, MovieCacheRepository cacheRepository) {
        this.restTemplate = restTemplate;
        this.cacheRepository = cacheRepository;
    }

    @RequestMapping("/{movieId}")
    public MovieSummary getMovieInfo(@PathVariable("movieId") String movieId) {

        // 1. Check cache first
        Optional<MovieCache> cached = cacheRepository.findById(movieId);
        if (cached.isPresent()) {
            System.out.println("Cache hit -> movieId: " + movieId);
            return toSummary(cached.get());
        }

        // 2. Cache miss - fetch from MovieDB
        System.out.println("Cache miss -> fetching from MovieDB: " + movieId);
        MovieSummary movie = restTemplate.getForObject(
                "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey,
                MovieSummary.class
        );

        // 3. Save to MongoDB cache
        assert movie != null;
        MovieCache toCache = new MovieCache(
                movieId,
                movie.getTitle(),
                movie.getOverview(),
                LocalDate.now()
        );
        cacheRepository.save(toCache);

        return movie;
    }

    private MovieSummary toSummary(MovieCache movieCache) {
        return new MovieSummary(
                movieCache.getMovieId(),
                movieCache.getMovieName(),
                movieCache.getDescription()
        );
    }
}
