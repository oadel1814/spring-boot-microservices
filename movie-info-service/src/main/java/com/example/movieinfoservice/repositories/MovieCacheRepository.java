package com.example.movieinfoservice.repositories;

import com.example.movieinfoservice.models.Movie;
import com.example.movieinfoservice.models.MovieCache;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieCacheRepository extends MongoRepository<MovieCache, String> {}
