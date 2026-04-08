package com.moviecatalogservice.resources;

import com.moviecatalogservice.TrendingServiceGrpc;
import  com.moviecatalogservice.Trending;
import com.moviecatalogservice.models.CatalogItem;
import com.moviecatalogservice.models.Rating;
import com.moviecatalogservice.services.MovieInfoService;
import com.moviecatalogservice.services.UserRatingService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
        return ratings.stream().map(movieInfoService::getCatalogItem).collect(Collectors.toList());
    }

    /**
     * NEW: Trending Movies Endpoint (via gRPC)
     * Test this at: http://localhost:8081/catalog/trending/5
     */
    @GetMapping("/trending/{limit}")
    public List<String> getTrending(@PathVariable("limit") int limit) {
        // We use the full package name for the gRPC Movie to avoid
        // conflicts with your local 'models.Movie' class
        com.moviecatalogservice.TopRequest request = com.moviecatalogservice.TopRequest.newBuilder()
                .setLimit(limit)
                .build();
        com.moviecatalogservice.TopResponse response = trendingClient.getTopMovies(request);

        List<com.moviecatalogservice.Movie> trendingMovies = response.getMoviesList();

        return trendingMovies.stream()
                .map(m -> "Ranked Movie: " + m.getTitle() + " | Avg Rating: " + m.getRating())
                .collect(Collectors.toList());
    }
}
