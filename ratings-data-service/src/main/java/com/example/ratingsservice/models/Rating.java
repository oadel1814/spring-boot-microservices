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
    private int userId;

    @Id
    @Column(name = "movie_id")
    private int movieId;

    @Column(name = "rating", nullable = false)
    private int rating;
}