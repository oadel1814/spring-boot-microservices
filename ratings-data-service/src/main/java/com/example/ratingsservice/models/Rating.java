package com.example.ratingsservice.models;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "ratings")
@IdClass(RatingId.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Rating {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "rating", nullable = false)
    private int rating;
}