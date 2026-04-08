package com.example.trendingservice.repositories.jpa;

import com.example.trendingservice.models.MovieCache;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieCacheRepository extends MongoRepository<MovieCache,String> {
    Optional<MovieCache> findByType(String type);
}
