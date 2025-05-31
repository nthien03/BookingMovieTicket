package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.MovieRequest;
import com.example.booking_movie_ticket.dto.response.movie.MovieCreateResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieDetailResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieListResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieResponse;
import com.example.booking_movie_ticket.entity.Actor;
import com.example.booking_movie_ticket.entity.Genre;
import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.ActorRepository;
import com.example.booking_movie_ticket.repository.GenreRepository;
import com.example.booking_movie_ticket.repository.MovieRepository;
import com.example.booking_movie_ticket.service.MovieService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;

    public MovieServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository, GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public MovieCreateResponse createMovie(MovieRequest request) {
        if (movieRepository.existsByMovieName(request.getMovieName())) {
            throw new AppException(ErrorCode.MOVIE_NAME_EXISTED);
        }

        Movie movie = new Movie();
        if (request.getActors() != null) {
            List<Actor> dbActors = this.actorRepository.findByIdIn(request.getActors());
            movie.setActors(dbActors);
        }
        if (request.getGenres() != null) {
            List<Genre> dbGenres = this.genreRepository.findByIdIn(request.getGenres());
            movie.setGenres(dbGenres);
        }
        movie.setMovieName(request.getMovieName());
        movie.setDirector(request.getDirector());
        movie.setDescription(request.getDescription());
        movie.setPoster(request.getPoster());
        movie.setTrailerUrl(request.getTrailerUrl());
        movie.setDuration(request.getDuration());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setAgeRestriction(request.getAgeRestriction());
        movie.setStatus(true);
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
                        movie.getReleaseDate(),
                        movie.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public MovieDetailResponse getMovieById(long movieId) {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));
        List<String> actors = movie.getActors().stream().map(actor -> actor.getFullName()).toList();
        List<String> genres = movie.getGenres().stream().map(genre -> genre.getName()).toList();


        return MovieDetailResponse.builder()
                .id(movie.getId())
                .movieName(movie.getMovieName())
                .director(movie.getDirector())
                .actors(actors)
                .duration(movie.getDuration())
                .poster(movie.getPoster())
                .trailerUrl(movie.getTrailerUrl())
                .genres(genres)
                .releaseDate(movie.getReleaseDate())
                .ageRestriction(movie.getAgeRestriction())
                .description(movie.getDescription())
                .status(movie.getStatus())
                .build();
    }


    @Override
    public MovieResponse updateMovie(long movieId, MovieRequest request) {
        return null;
    }

    @Override
    public List<MovieListResponse> searchNowShowing(String keyword) {
        Instant now = Instant.now();
        if (keyword != null && keyword.isBlank()) keyword = null;
        List<Movie> movies = movieRepository.searchNowShowingByName(now, keyword);
        return movies.stream()
                .map(movie -> new MovieListResponse(
                        movie.getId(),
                        movie.getMovieName(),
                        movie.getPoster(),
                        movie.getDuration(),
                        movie.getReleaseDate(),
                        movie.getStatus()))
                .toList();
    }

    @Override
    public MovieResponse changeStatus(long movieid) {
        return null;
    }
}

