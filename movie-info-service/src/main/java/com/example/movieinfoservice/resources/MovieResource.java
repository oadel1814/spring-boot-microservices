package com.example.movieinfoservice.resources;

import com.example.movieinfoservice.models.Movie;
import com.example.movieinfoservice.models.MovieSummary;
import com.example.movieinfoservice.repositories.MovieCacheRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
@RestController
@RequestMapping("/movies")
public class MovieResource {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final MovieCacheRepository cacheRepository;

    private static final String TMDB_URL = "https://api.themoviedb.org/3/movie/";

    public MovieResource(RestTemplate restTemplate, MovieCacheRepository cacheRepository) {
        this.restTemplate    = restTemplate;
        this.cacheRepository = cacheRepository;
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<?> getMovieInfo(@PathVariable String movieId) {
        return cacheRepository.findById(movieId)
                .map(cached -> {
                    System.out.println("Cache hit -> movieId: " + movieId);
                    return ResponseEntity.ok(cached);
                })
                .orElseGet(() -> {
                    System.out.println("Cache miss -> movieId: " + movieId);
                    Movie movie = fetchFromTmdb(movieId);
                    cacheRepository.save(movie);
                    return ResponseEntity.ok(movie);
                });
    }

    @DeleteMapping("/cache")
    public ResponseEntity<String> clearCache() {
        cacheRepository.deleteAll();
        System.out.println("Cache cleared!");
        return ResponseEntity.ok("Cache cleared successfully!");
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatus(
            ResponseStatusException ex, HttpServletRequest request) {
        return buildError(ex.getStatus(), ex.getReason(), request.getRequestURI());
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            HttpClientErrorException.NotFound ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, "Movie not found on TMDB", request.getRequestURI());
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(
            HttpClientErrorException.Unauthorized ex, HttpServletRequest request) {
        return buildError(HttpStatus.UNAUTHORIZED, "Invalid TMDB API key — check your api.key config", request.getRequestURI());
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Map<String, Object>> handleRestClient(
            RestClientException ex, HttpServletRequest request) {
        return buildError(HttpStatus.SERVICE_UNAVAILABLE, "Could not reach TMDB: " + ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(
            Exception ex, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage(), request.getRequestURI());
    }


    private Movie fetchFromTmdb(String movieId) {
        String url = TMDB_URL + movieId + "?api_key=" + apiKey;
        MovieSummary summary = restTemplate.getForObject(url, MovieSummary.class);
        if (summary == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found on TMDB");
        return new Movie(movieId, summary.getTitle(), summary.getOverview());
    }

    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String message, String path) {
        return ResponseEntity.status(status).body(Map.of(
                "status",    status.value(),
                "error",     status.getReasonPhrase(),
                "message",   message != null ? message : "Unexpected error",
                "path",      path,
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}