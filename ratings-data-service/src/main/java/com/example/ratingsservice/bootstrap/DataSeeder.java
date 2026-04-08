package com.example.ratingsservice.bootstrap;

import com.example.ratingsservice.models.Rating;
import com.example.ratingsservice.repositories.RatingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RatingRepository ratingRepository;

    public DataSeeder(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void run(String... args) {
        if (ratingRepository.count() > 0) {
            System.out.println("Data already seeded, skipping...");
            return;
        }

        Random random = new Random();
        List<Rating> ratings = new ArrayList<>();

        for (int movie = 1; movie <= 50; movie++) {
            for (int userId = 1; userId <= 50; userId++) {
                int score = (movie <= 10)
                        ? 4 + random.nextInt(2)   // 4–5
                        : 1 + random.nextInt(5);  // 1–5

                Rating rating = new Rating();
                rating.setUserId(String.valueOf(userId));
                rating.setMovieId(String.valueOf(movie));
                rating.setRating(score);
                ratings.add(rating);
            }
        }

        ratingRepository.saveAll(ratings);
        System.out.println("Seeded " + ratings.size() + " ratings successfully!");
    }
}