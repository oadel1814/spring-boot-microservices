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
    public ResponseEntity<Movie> getMovieInfo(@PathVariable String movieId) {
        // Cache hit
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
    public ResponseEntity<Map<String, Object>> handleException(ResponseStatusException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(Map.of(
                "status",  ex.getStatus().value(),
                "message", ex.getReason() != null ? ex.getReason() : "Unexpected error"
        ));
    }

    private Movie fetchFromTmdb(String movieId) {
        try {
            String url = TMDB_URL + movieId + "?api_key=" + apiKey;
            MovieSummary summary = restTemplate.getForObject(url, MovieSummary.class);

            if (summary == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");

            return new Movie(movieId, summary.getTitle(), summary.getOverview());

        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie ID not found: " + movieId);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid TMDB API key");
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), "TMDB error: " + e.getMessage());
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Could not reach TMDB");
        }
    }
}