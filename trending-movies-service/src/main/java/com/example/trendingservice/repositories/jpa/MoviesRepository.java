package com.example.trendingservice.repositories.jpa;

import com.example.trendingservice.models.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MoviesRepository extends CrudRepository<Movie,Integer> {
    @Query(value = "SELECT movie_id , AVG(rating) as avg_rating FROM ratings  GROUP BY movie_id order by avg_rating DESC LIMIT :number  ",nativeQuery = true)
    List<Movie> getTopByRating(@Param("number") int number);
}
