package com.example.trendingservice.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Document(collection = "movie_cache")
@Data
/**
 * i have chosen time based caching (ttl).
 * we can make a=caching valid for 10 minutes
 */

public class MovieCache {
    @Id
    private String id;
    private String type; //  "trending"
    private List<Movie> movies; // The list retrieved from MySQL
    private LocalDateTime updatedAt; // To check if the cache is expired

}