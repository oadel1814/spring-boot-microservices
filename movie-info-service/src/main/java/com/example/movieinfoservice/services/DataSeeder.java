package com.example.movieinfoservice.services;

import com.example.movieinfoservice.models.Movie;
import com.example.movieinfoservice.repositories.MovieDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private MovieDBRepository movieDBRepository;

    @Override
    public void run(String... args) throws Exception {
        if (movieDBRepository.count() == 0) {
            movieDBRepository.saveAll(Arrays.asList(
                    new Movie("1", "The Shawshank Redemption", "Two imprisoned men bond over years finding solace and redemption."),
                    new Movie("2", "The Godfather", "The aging patriarch of an organized crime dynasty transfers control to his son."),
                    new Movie("3", "The Dark Knight", "Batman raises the stakes in his war on crime against the Joker."),
                    new Movie("4", "Pulp Fiction", "The lives of two mob hitmen, a boxer, and a pair of bandits intertwine."),
                    new Movie("5", "Forrest Gump", "The presidencies of Kennedy through Clinton through the eyes of an Alabama man."),
                    new Movie("6", "Inception", "A thief who steals corporate secrets through dream-sharing technology."),
                    new Movie("7", "The Matrix", "A computer hacker learns about the true nature of his reality."),
                    new Movie("8", "Goodfellas", "The story of Henry Hill and his life in the mob."),
                    new Movie("9", "Interstellar", "A team of explorers travel through a wormhole in space."),
                    new Movie("10", "The Silence of the Lambs", "A young FBI cadet must receive help of an incarcerated cannibal killer."),
                    new Movie("11", "Schindler's List", "In German-occupied Poland, Oskar Schindler saves the lives of more than a thousand Jewish refugees."),
                    new Movie("12", "The Lord of the Rings: The Return of the King", "Gandalf and Aragorn lead the World of Men against Sauron's army."),
                    new Movie("13", "Fight Club", "An insomniac office worker forms an underground fight club with a soap salesman."),
                    new Movie("14", "Star Wars: A New Hope", "Luke Skywalker joins forces with a Jedi Knight to rescue a princess."),
                    new Movie("15", "The Empire Strikes Back", "After the rebels are overpowered by the Empire, Luke begins Jedi training with Yoda."),
                    new Movie("16", "Return of the Jedi", "After rescuing Han Solo, the rebels attempt to destroy the second Death Star."),
                    new Movie("17", "Gladiator", "A former Roman General sets out to exact vengeance against the corrupt emperor."),
                    new Movie("18", "The Green Mile", "The lives of guards on Death Row are affected by one of their charges."),
                    new Movie("19", "Saving Private Ryan", "Following the Normandy Landings, a group of U.S. soldiers go behind enemy lines."),
                    new Movie("20", "Braveheart", "Scottish warrior William Wallace leads his countrymen in a rebellion against England."),
                    new Movie("21", "The Departed", "An undercover cop and a mole in the police attempt to identify each other."),
                    new Movie("22", "Memento", "A man with short-term memory loss attempts to track down his wife's murderer."),
                    new Movie("23", "The Prestige", "Two stage magicians engage in a competitive rivalry in turn-of-the-century London."),
                    new Movie("24", "Whiplash", "A promising young drummer enrolls at a cut-throat music conservatory."),
                    new Movie("25", "The Grand Budapest Hotel", "The adventures of a legendary concierge and his protege at a famous hotel."),
                    new Movie("26", "12 Angry Men", "A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence."),
                    new Movie("27", "Se7en", "Two detectives hunt a serial killer who uses the seven deadly sins as motives."),
                    new Movie("28", "The Usual Suspects", "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat."),
                    new Movie("29", "Léon: The Professional", "A 12-year-old girl is reluctantly taken in by a professional assassin."),
                    new Movie("30", "American History X", "A former neo-nazi skinhead tries to prevent his younger brother from going down the same path."),
                    new Movie("31", "Apocalypse Now", "A special forces officer journeys into Cambodia to assassinate a renegade colonel."),
                    new Movie("32", "Taxi Driver", "A mentally unstable Vietnam war veteran works as a night-time taxi driver in New York City."),
                    new Movie("33", "Raging Bull", "The life of boxer Jake LaMotta, whose violence and temper destroyed his life and career."),
                    new Movie("34", "Chinatown", "A private detective hired to expose an adulterer finds himself caught up in a web of deceit."),
                    new Movie("35", "2001: A Space Odyssey", "After discovering a mysterious monolith, humanity embarks on a journey into space."),
                    new Movie("36", "A Clockwork Orange", "In a dystopian future, a violent gang leader undergoes an experimental aversion therapy."),
                    new Movie("37", "The Shining", "A family heads to an isolated hotel for the winter where an evil presence influences the father."),
                    new Movie("38", "Full Metal Jacket", "A pragmatic U.S. Marine observes the dehumanizing effects of the Vietnam war."),
                    new Movie("39", "Blade Runner", "A blade runner must pursue and terminate four replicants who have stolen a ship."),
                    new Movie("40", "Alien", "The crew of a commercial spacecraft encounters a deadly extraterrestrial creature."),
                    new Movie("41", "Terminator 2: Judgment Day", "A cyborg, identical to the one who failed to kill Sarah Connor, is sent to protect her son."),
                    new Movie("42", "Back to the Future", "A teenager is accidentally sent thirty years into the past in a time-traveling DeLorean."),
                    new Movie("43", "E.T. the Extra-Terrestrial", "A troubled child summons the courage to help a friendly alien escape Earth."),
                    new Movie("44", "Raiders of the Lost Ark", "Archaeologist and adventurer Indiana Jones is hired by the U.S. government to find the Ark."),
                    new Movie("45", "Jurassic Park", "A pragmatic paleontologist visiting an almost-complete theme park is tasked with confirming it is safe."),
                    new Movie("46", "The Lion King", "Lion cub and future king Simba searches for his identity after his father's murder."),
                    new Movie("47", "Toy Story", "A cowboy doll is profoundly threatened and jealous when a new spaceman figure supplants him."),
                    new Movie("48", "Up", "A 78-year-old balloon salesman fulfills his dream to see the wilds of South America by tying balloons to his house."),
                    new Movie("49", "WALL-E", "In the distant future, a small waste-collecting robot inadvertently embarks on a space journey."),
                    new Movie("50", "Spirited Away", "During her family's move to the suburbs, a sullen 10-year-old girl wanders into a world ruled by gods and spirits.")
            ));
            System.out.println("Movies seeded successfully!");
        }
    }
}