package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.MovieRequest;
import com.example.booking_movie_ticket.dto.response.MovieCreateResponse;
import com.example.booking_movie_ticket.dto.response.MovieListResponse;
import com.example.booking_movie_ticket.dto.response.MovieResponse;
import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.repository.MovieRepository;
import com.example.booking_movie_ticket.service.MovieService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieCreateResponse createMovie(MovieRequest request) {
        Movie movie = Movie.builder()
                .movieName(request.getMovieName())
                .actor(request.getActors())
                .director(request.getDirector())
                .description(request.getDescription())
                .poster(request.getPoster())
                .trailerUrl(request.getTrailerUrl())
                .duration(request.getDuration())
                .genre(request.getGenre())
                .releaseDate(request.getReleaseDate())
                .ageRestriction(request.getAgeRestriction())
                .status(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
        Movie savedMovie = movieRepository.save(movie);
        return new MovieCreateResponse(savedMovie.getId());
    }

    @Override
    public List<MovieListResponse> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> new MovieListResponse(
                        movie.getId(),
                        movie.getMovieName(),
                        movie.getPoster(),
                        movie.getDuration(),
                        movie.getReleaseDate()))
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponse getMovie(long movieId) {
        return null;
    }

    @Override
    public MovieResponse updateMovie(long movieId, MovieRequest request) {
        return null;
    }

    @Override
    public MovieResponse changeStatus(long movieid) {
        return null;
    }
}

