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
    private int movie_id;

    @Column(name = "avg_rating")
    private double rating;

    // Don't forget your Getters/Setters or @Data from Lombok!
}