package com.example.trendingservice.service;

import com.example.trendingservice.TopRequest;

import com.example.trendingservice.service.MovieInfoCacheService;
import com.example.trendingservice.TopResponse;
import com.example.trendingservice.TrendingServiceGrpc;
import com.example.trendingservice.dto.MovieDTO;
import com.example.trendingservice.models.Movie;
import io.grpc.stub.StreamObserver;
import java.util.List;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@GrpcService
public class TrendingService extends TrendingServiceGrpc.TrendingServiceImplBase {

  @Autowired private MoviesService moviesService;
  @Autowired private RestTemplate restTemplate;
  @Autowired private MovieInfoCacheService movieInfoCacheService;

  @Override
  public void getTopMovies(TopRequest request, StreamObserver<TopResponse> responseObserver) {
    int limit = Math.max(0, request.getLimit());
    List<Movie> movies = moviesService.getMovies(limit, "trending");
    TopResponse.Builder responseBuilder = TopResponse.newBuilder();

    for (Movie movie : movies) {
      int movieId = movie.getMovie_id();
      double movieRating = movie.getRating();

      MovieDTO movieDto = movieInfoCacheService.getMovieInfo(movieId);

      com.example.trendingservice.Movie.Builder filmBuilder =
          com.example.trendingservice.Movie.newBuilder();
      filmBuilder.setId(movieId);
      filmBuilder.setRating(movieRating);
      filmBuilder.setTitle(
          movieDto != null && movieDto.getName() != null
              ? movieDto.getName()
              : "Movie Name not found");
      responseBuilder.addMovies(filmBuilder);
    }

    responseObserver.onNext(responseBuilder.build());
    responseObserver.onCompleted();
  }
}
