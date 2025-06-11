package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.MovieRequest;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieCreateResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieDetailResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieListResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieResponse;
import com.example.booking_movie_ticket.entity.*;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.ActorRepository;
import com.example.booking_movie_ticket.repository.GenreRepository;
import com.example.booking_movie_ticket.repository.MovieRepository;
import com.example.booking_movie_ticket.repository.ScheduleRepository;
import com.example.booking_movie_ticket.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private final ScheduleRepository scheduleRepository;

    public MovieServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository, GenreRepository genreRepository, ScheduleRepository scheduleRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.genreRepository = genreRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public MovieCreateResponse createMovie(MovieRequest request) {
        if (movieRepository.existsByMovieName(request.getMovieName().trim())) {
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
        movie.setMovieName(request.getMovieName().trim().toUpperCase());
        movie.setDirector(request.getDirector().trim());
        movie.setDescription(request.getDescription().trim());
        movie.setPoster(request.getPoster());
        movie.setTrailerUrl(request.getTrailerUrl().trim());
        movie.setDuration(request.getDuration());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setAgeRestriction(request.getAgeRestriction());
        movie.setStatus(request.getStatus());
        Movie savedMovie = movieRepository.save(movie);
        return new MovieCreateResponse(savedMovie.getId());
    }

    @Override
    public PageResponse getAllMovies(Specification<Movie> spec, Pageable pageable) {
        Page<Movie> moviePage = this.movieRepository.findAll(spec, pageable);
        List<MovieListResponse> movieList = moviePage.getContent()
                .stream()
                .map(movie -> MovieListResponse.builder()
                        .id(movie.getId())
                        .movieName(movie.getMovieName())
                        .poster(movie.getPoster())
                        .duration(movie.getDuration())
                        .releaseDate(movie.getReleaseDate())
                        .status(movie.getStatus())
                        .build()
                ).toList();

        MetaPage metaPage = new MetaPage();
        metaPage.setPage(pageable.getPageNumber() + 1);
        metaPage.setPageSize(pageable.getPageSize());
        metaPage.setPages(moviePage.getTotalPages());
        metaPage.setTotal(moviePage.getTotalElements());

        return PageResponse.builder()
                .meta(metaPage)
                .result(movieList)
                .build();
    }

//    @Override
//    public List<MovieListResponse> getAllMovies() {
//        List<Movie> movies = movieRepository.findAll();
//        return movies.stream()
//                .map(movie -> new MovieListResponse(
//                        movie.getId(),
//                        movie.getMovieName(),
//                        movie.getPoster(),
//                        movie.getDuration(),
//                        movie.getReleaseDate(),
//                        movie.getStatus()))
//                .collect(Collectors.toList());
//    }

    @Override
    public MovieDetailResponse getMovieById(long movieId) {
        Movie movie = this.movieRepository.findById(movieId).orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));
        List<MovieDetailResponse.ActorInMovie> actors = movie.getActors().stream()
                .map(actor -> new MovieDetailResponse.ActorInMovie(
                        actor.getId(),
                        actor.getFullName())
                ).toList();

        List<MovieDetailResponse.GenreInMovie> genres = movie.getGenres().stream()
                .map(genre -> new MovieDetailResponse.GenreInMovie(
                        genre.getId(),
                        genre.getName()))
                .toList();


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
    public void updateMovie(long movieId, MovieRequest request) {
        Movie movie = this.movieRepository.findById(movieId)
                .orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));

        Optional<Movie> existing = movieRepository
                .findByMovieName(request.getMovieName().trim());

        if (existing.isPresent() &&
                !existing.get().getId().equals(movieId)) {
            throw new AppException(ErrorCode.MOVIE_NAME_EXISTED);
        }

        if (request.getActors() != null) {
            List<Actor> dbActors = this.actorRepository.findByIdIn(request.getActors());
            movie.setActors(dbActors);
        }
        if (request.getGenres() != null) {
            List<Genre> dbGenres = this.genreRepository.findByIdIn(request.getGenres());
            movie.setGenres(dbGenres);
        }
        movie.setMovieName(request.getMovieName().trim().toUpperCase());
        movie.setDirector(request.getDirector().trim());
        movie.setDescription(request.getDescription().trim());
        movie.setPoster(request.getPoster());
        movie.setTrailerUrl(request.getTrailerUrl().trim());
        movie.setDuration(request.getDuration());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setAgeRestriction(request.getAgeRestriction());
        movie.setStatus(request.getStatus());
        this.movieRepository.save(movie);

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
    public List<MovieListResponse> getComingSoonMovies() {
        Instant now = Instant.now();
        List<Movie> movies = movieRepository.findByReleaseDateAfterAndStatusIsTrueOrderByReleaseDateAsc(now);
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

    public Map<LocalDate, List<MovieListResponse>> getUpcomingMoviesWithSchedules() {
        ZoneId vnZone = ZoneId.of("Asia/Ho_Chi_Minh");

        // Lấy đầu ngày hôm nay ở VN (00:00)
        ZonedDateTime today = LocalDate.now(vnZone).atStartOfDay(vnZone);

        // Chuyển thành Instant (UTC)
        Instant startDate = today.toInstant();

        ZonedDateTime toDate = LocalDate.now(vnZone)
                .plusDays(4)
                .atTime(LocalTime.MAX)  // 23:59:59.999999999
                .atZone(vnZone);

        Instant endDate = toDate.toInstant();

        Instant now = Instant.now();

        List<Schedule> schedules = scheduleRepository.findSchedulesInRange(startDate, endDate, now);

        // Group by date
        return schedules.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getDate().atZone(vnZone).toLocalDate(),                          // nhóm theo ngaỳ
                        Collectors.collectingAndThen(                                       // sau khi nhóm, tiep tuc xu ly du lieu ben trong moi nhom
                                Collectors.mapping(Schedule::getMovie, Collectors.toSet()), // map movie lay tu Schedule vao Set de tranh trung phim
                                movieSet -> movieSet.stream()
                                        .map(m -> MovieListResponse.builder()
                                                .id(m.getId())
                                                .movieName(m.getMovieName())
                                                .poster(m.getPoster())
                                                .duration(m.getDuration())
                                                .releaseDate(m.getReleaseDate())
                                                .status(m.getStatus())
                                                .build())
                                        .collect(Collectors.toList())
                        )
                ));
    }

    @Override
    public MovieResponse changeStatus(long movieid) {
        return null;
    }
}

