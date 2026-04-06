USE ratings_db;

CREATE TABLE IF NOT EXISTS ratings (
                                       user_id INT NOT NULL,
                                       movie_id INT NOT NULL,
                                       rating INT NOT NULL,
                                       PRIMARY KEY (user_id, movie_id)
);