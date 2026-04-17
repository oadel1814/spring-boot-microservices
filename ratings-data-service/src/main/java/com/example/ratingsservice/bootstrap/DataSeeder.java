package com.ratingsdataservice.bootstrap;

import com.ratingsdataservice.models.Rating;
import com.ratingsdataservice.repository.RatingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RatingRepository ratingRepository;

    public DataSeeder(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void run(String... args) {
        // Avoid duplicating data every restart
        if (ratingRepository.count() > 0) {
            return;
        }

        Random random = new Random();

        // Generate ratings for 50 movies
        for (int movie = 1; movie <= 50; movie++) {

            String movieId = "movie" + movie;

            // 50 ratings per movie
            for (int i = 0; i < 50; i++) {

                int score;

                // Make first 10 movies clearly "top trending"
                if (movie <= 10) {
                    score = 4 + random.nextInt(2); // 4–5
                } else {
                    score = 1 + random.nextInt(5); // 1–5
                }

                Rating rating = new Rating();
                rating.setMovieId(movieId);
                rating.setRating(score);

                ratingRepository.save(rating);
            }
        }

        System.out.println("Rating data seeded successfully!");
    }
}