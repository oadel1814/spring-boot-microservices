package com.example.trendingservice.dto;


import lombok.*;
import org.springframework.context.annotation.Bean;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MovieDTO implements Serializable{

    private String movieId;
    private String name;
    private String description;

}
