package com.example.trendingservice.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ratings")
@Data
public class Movie {
    @Id
    private Integer movie_id;
    @Column(name = "avg_rating")
    private Double rating;

    // Don't forget your Getters/Setters or @Data from Lombok!
}