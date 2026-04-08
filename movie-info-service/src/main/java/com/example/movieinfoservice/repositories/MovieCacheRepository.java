package com.example.movieinfoservice.repositories;

import com.example.movieinfoservice.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieCacheRepository extends MongoRepository<Movie, String> {}
