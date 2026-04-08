package com.example.trendingservice.service;
import com.example.trendingservice.dto.MovieDTO;
import com.example.trendingservice.models.Movie;
import com.example.trendingservice.TrendingServiceGrpc;
import io.grpc.stub.StreamObserver;

import com.example.trendingservice.TopResponse;
import com.example.trendingservice.TopRequest;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.trendingservice.templates.RestTemplateConfig;
import org.springframework.boot.web.client.RestTemplateBuilder;
import com.example.trendingservice.templates.RestTemplateConfig;
import org.springframework.web.client.RestTemplate;


import java.util.List;


@GrpcService
public class TrendingService extends TrendingServiceGrpc.TrendingServiceImplBase {

    @Autowired
    private MoviesService moviesService;


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void getTopMovies(TopRequest request, StreamObserver<TopResponse> responseObserver){
        int limit =Math.max(0,request.getLimit());
        List<Movie> movies =moviesService.getMovies(limit,"trending");
        TopResponse.Builder responseBuilder = TopResponse.newBuilder();
        for(Movie movie:movies) {
            int movieId=movie.getMovie_id();
            double movieRating=movie.getRating();
            String title="Unknown";
            //getting name till now ,but it is not the most performant way(am using rest )
            MovieDTO movieDto=new MovieDTO("","","");
            try{movieDto =
                    restTemplate.getForObject(
                            "http://movie-info-service/movies/" + movieId,
                            com.example.trendingservice.dto.MovieDTO.class
                    );}
            catch (Exception e){
                title="Unknown";
            }

            if (movieDto.getName() != null) {
                title = movieDto.getName();
            }
//            title="fight club";

            com.example.trendingservice.Movie.Builder filemBuilder =com.example.trendingservice.Movie.newBuilder();
            filemBuilder.setId(movieId);
            filemBuilder.setRating(movieRating);
            filemBuilder.setTitle(title);
            responseBuilder.addMovies(filemBuilder);


        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
