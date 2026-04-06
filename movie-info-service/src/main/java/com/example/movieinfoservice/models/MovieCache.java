package com.example.movieinfoservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Document(collection = "movies")
public class MovieCache extends Movie{
    @Id
    private String  movieId;
    private String movieName;
    private String description;
    private LocalDate cachedAt;
}
