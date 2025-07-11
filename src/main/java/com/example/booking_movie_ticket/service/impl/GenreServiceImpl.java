package com.example.booking_movie_ticket.service.impl;


import com.example.booking_movie_ticket.dto.request.GenreRequest;
import com.example.booking_movie_ticket.dto.response.genre.GenreResponse;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.entity.Genre;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.GenreRepository;
import com.example.booking_movie_ticket.service.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public long createGenre(GenreRequest request) {
        if (genreRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.GENRE_EXISTED);
        }
        Genre genre = Genre.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(true)
                .build();
        Genre savedgenre = genreRepository.save(genre);
        return savedgenre.getId();

    }

    @Override
    public PageResponse getAllGenres(Specification<Genre> spec, Pageable pageable) {
        Page<Genre> genrePage = this.genreRepository.findAll(spec, pageable);
        List<GenreResponse> genreList = genrePage.getContent()
                .stream()
                .map(genre -> GenreResponse.builder()
                        .id(genre.getId())
                        .name(genre.getName())
                        .description(genre.getDescription())
                        .status(genre.getStatus())
                        .build()
                ).toList();

        MetaPage metaPage = new MetaPage();
        metaPage.setPage(pageable.getPageNumber() + 1);
        metaPage.setPageSize(pageable.getPageSize());
        metaPage.setPages(genrePage.getTotalPages());
        metaPage.setTotal(genrePage.getTotalElements());

        return PageResponse.builder()
                .meta(metaPage)
                .result(genreList)
                .build();
    }


    @Override
    public GenreResponse getGenre(long genreId) {
        Genre genre = this.genreRepository.findById(genreId).orElseThrow(() -> new AppException(ErrorCode.GENRE_NOT_EXISTED));
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .description(genre.getDescription())
                .status(genre.getStatus())
                .build();
    }

    @Override
    public void updateGenre(long genreId, GenreRequest request) {

        // check exist by id
        Genre genre = this.genreRepository.findById(genreId)
                .orElseThrow(() -> new AppException(ErrorCode.GENRE_NOT_EXISTED));

//        // Kiểm tra nếu tên mới đã tồn tại ở genre khác
//        Optional<Genre> genreWithSameName = genreRepository.findByName(request.getName());
//        if (genreWithSameName.isPresent() && !genreWithSameName.get().getId().equals(genreId)) {
//            throw new AppException(ErrorCode.GENRE_EXISTED);
//        }

        if (genreRepository.existsByName(request.getName().trim())) {
            throw new AppException(ErrorCode.GENRE_EXISTED);
        }
        genre.setName(request.getName());
        genre.setDescription(request.getDescription());
        genre.setStatus(request.getStatus());
        this.genreRepository.save(genre);

        log.info("Genre has updated successfully, genreId={}", genre.getId());
    }

    @Override
    public GenreResponse changeStatus(long genreId) {
        return null;
    }
}
