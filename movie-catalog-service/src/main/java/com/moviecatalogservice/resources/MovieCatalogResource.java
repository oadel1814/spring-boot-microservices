package com.moviecatalogservice.resources;

import com.moviecatalogservice.TrendingServiceGrpc;
import  com.moviecatalogservice.Trending;
import com.moviecatalogservice.models.CatalogItem;
import com.moviecatalogservice.models.Rating;
import com.moviecatalogservice.services.MovieInfoService;
import com.moviecatalogservice.services.UserRatingService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    private final RestTemplate restTemplate;
    private final MovieInfoService movieInfoService;
    private final UserRatingService userRatingService;

    @GrpcClient("trendingService")
    private TrendingServiceGrpc.TrendingServiceBlockingStub trendingClient;

    public MovieCatalogResource(RestTemplate restTemplate,
                                MovieInfoService movieInfoService,
                                UserRatingService userRatingService) {
        this.restTemplate = restTemplate;
        this.movieInfoService = movieInfoService;
        this.userRatingService = userRatingService;
    }

    /**
     * Existing User Catalog Endpoint
     */
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        List<Rating> ratings = userRatingService.getUserRating(userId).getRatings();
        System.out.println("Ratings for user " + userId + ": " + ratings.toString());
        return ratings.stream().map(movieInfoService::getCatalogItem).collect(Collectors.toList());
    }

    /**
     * NEW: Trending Movies Endpoint (via gRPC)
     * Test this at: http://localhost:8085/catalog/trending/5
     */
    @GetMapping("/trending/{limit}")
    public ResponseEntity<?> getTrending(@PathVariable int limit) {
        try {
            com.moviecatalogservice.TopRequest request = com.moviecatalogservice.TopRequest.newBuilder()
                    .setLimit(limit)
                    .build();

            com.moviecatalogservice.TopResponse response = trendingClient.getTopMovies(request);

            List<Map<String, Object>> result = response.getMoviesList().stream()
                    .map(movie -> {
                        Map<String, Object> map = new java.util.HashMap<>();
                        map.put("id",     movie.getId());
                        map.put("title",  movie.getTitle());
                        map.put("rating", movie.getRating());
                        return map;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(result);

        } catch (io.grpc.StatusRuntimeException e) {
            return ResponseEntity.status(503).body(Map.of(
                    "error",   "Trending service unavailable",
                    "message", e.getStatus().toString()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error",   "Unexpected error",
                    "message", e.getMessage()
            ));
        }
    }




}
