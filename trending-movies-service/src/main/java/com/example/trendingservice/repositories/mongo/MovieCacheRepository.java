package com.example.trendingservice.repositories.mongo;

import com.example.trendingservice.models.MovieCache;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieCacheRepository extends MongoRepository<MovieCache,String> {
    List<MovieCache> findByType(String type);
}
