package com.example.trendingservice.dto;


import lombok.*;
import org.springframework.context.annotation.Bean;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MovieDTO {

    private String movieId;
    private String name;
    private String description;

}
