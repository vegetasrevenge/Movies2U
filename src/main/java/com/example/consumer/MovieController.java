package com.example.consumer;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Result;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class MovieController {

    @RequestMapping("/")
    public String homePage () {

        return "home";
    }

    @RequestMapping("/now-playing")
    public String nowPlaying (Model model) {
        model.addAttribute("movies", getMovies());
        return "now-playing";
    }

    @RequestMapping("/medium-popular")
    public String medPop (Model model) {
        List<Movie> mediumPopular = getMovies().stream()
                                    .filter(movie -> movie.getPopularity() > 30 && movie.getPopularity() < 80)
                                    .collect(Collectors.toList());
        model.addAttribute("movies", mediumPopular);
        System.out.println(mediumPopular);
        return "medium-popular";
    }

    public static List<Movie> getMovies() {
        RestTemplate restTemplate = new RestTemplate();
        ResultsPage movies = restTemplate.getForObject("https://api.themoviedb.org/3/movie/now_playing?api_key=be2a38521a7859c95e2d73c48786e4bb", ResultsPage.class);
        return movies.getResults();
    }

}
