package com.example.trendingservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;


@Data
@AllArgsConstructor
public class MovieDTO {

    private String movieId;
    private String name;
    private String description;

}
