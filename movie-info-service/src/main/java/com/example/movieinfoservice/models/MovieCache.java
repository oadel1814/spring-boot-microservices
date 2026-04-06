package com.example.movieinfoservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Document(collection = "movies")
public class MovieCache{
    @Id
    private String  movieId;
    private String cachedMovieName;
    private String cachedDescription;
    private LocalDate cachedAt;
}
