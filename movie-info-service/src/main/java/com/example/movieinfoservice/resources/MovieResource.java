package com.example.movieinfoservice.resources;

import com.example.movieinfoservice.models.Movie;
import com.example.movieinfoservice.models.MovieCache;
import com.example.movieinfoservice.models.MovieSummary;
import com.example.movieinfoservice.repositories.MovieCacheRepository;
import com.example.movieinfoservice.repositories.MovieDBRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private MovieDBRepository dbRepository;

    public MovieResource(RestTemplate restTemplate,
                         MovieCacheRepository cacheRepository,
                         MovieDBRepository dbRepository) {
        this.restTemplate = restTemplate;
        this.cacheRepository = cacheRepository;
        this.dbRepository = dbRepository;
    }

    @RequestMapping("/{movieId}")
    public MovieSummary getMovieInfo(@PathVariable("movieId") String movieId) throws InterruptedException {

        // 1. Check cache first
        Optional<MovieCache> cached = cacheRepository.findById(movieId);
        if (cached.isPresent()) {
            System.out.println("Cache hit -> movieId: " + movieId);
            return toSummary(cached.get());
        }

        // 2. Cache miss - fetch from DB with simulated delay
        System.out.println("Cache miss -> Fetching from DB for movieId: " + movieId);
        Thread.sleep(3000); // simulate slow external API call

        // 3. Fetch from MovieDB collection
        Movie movie = dbRepository.findById(movieId)
                .orElse(null);

        if (movie == null) {
            System.out.println("Movie not found for id: " + movieId);
            return new MovieSummary(movieId, "Movie Not Found", "No movie found with id: " + movieId);
        }

        // 4. Save to MongoDB cache
        MovieCache toCache = new MovieCache(
                movieId,
                movie.getName(),
                movie.getDescription(),
                LocalDate.now()
        );
        cacheRepository.save(toCache);

        // 5. Return as MovieSummary
        return new MovieSummary(
                movie.getId(),
                movie.getName(),
                movie.getDescription()
        );
    }

    private MovieSummary toSummary(MovieCache movieCache) {
        return new MovieSummary(
                movieCache.getMovieId(),
                movieCache.getCachedMovieName(),
                movieCache.getCachedDescription()
        );
    }

    @DeleteMapping("/cache")
    public String clearCache() {
        cacheRepository.deleteAll();
        System.out.println("Cache cleared!");
        return "Cache cleared successfully!";
    }
}
