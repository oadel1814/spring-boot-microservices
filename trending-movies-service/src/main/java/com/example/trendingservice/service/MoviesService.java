package com.example.trendingservice.service;

import com.example.trendingservice.models.Movie;
import com.example.trendingservice.models.MovieCache;
import com.example.trendingservice.repositories.jpa.MoviesRepository;
import com.example.trendingservice.repositories.mongo.MovieCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MoviesService {

    @Autowired
    private  MoviesRepository moviesRepository;

    @Autowired
    private  MovieCacheRepository cache;





    @Transactional
      public void setFreshData(List<Movie> data ,String type){
        MovieCache movieCache =new MovieCache();
        movieCache.setMovies(data);
        movieCache.setUpdatedAt(LocalDateTime.now());
        movieCache.setType(type);
        cache.save(movieCache);
    }



    @Transactional
    public List<Movie> getMovies(Integer limit,String type){

        // exist in cache
        var cachedData=cache.findByType(type);
        //read from cache (mongodb)
        if(!cachedData.isEmpty()  && cachedData.get(0).getUpdatedAt().plusMinutes(10).isAfter(LocalDateTime.now())){
            System.out.println("Cache hit in Trending Service for type: " + type);
           return  cachedData.get(0).getMovies();
        }

        // read from mySql
        System.out.println("Cache miss in Trending Service for type: " + type + ". Fetching from MySQL...");
        List<Movie> data = new ArrayList<>(moviesRepository.getTopByRating(limit));
        setFreshData(data,type);
        return  data;
    }
}

