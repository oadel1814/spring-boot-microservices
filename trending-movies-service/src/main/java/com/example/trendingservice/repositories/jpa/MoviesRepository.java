package com.example.trendingservice.repositories.mongo;

import com.example.trendingservice.models.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MoviesRepository extends CrudRepository<Movie,Integer> {
    @Query(value = "SELECT movie_id , AVG(rating) FROM ratings  GROUP BY movie_id order by `AVG(rating)` LIMIT  10",nativeQuery = true)
    Optional<Movie> getTopByRating();

}
