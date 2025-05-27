package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.ActorRequest;
import com.example.booking_movie_ticket.dto.response.actor.ActorCreateResponse;
import com.example.booking_movie_ticket.dto.response.actor.ActorResponse;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.entity.Actor;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.ActorRepository;
import com.example.booking_movie_ticket.service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {

    private static final Logger log = LoggerFactory.getLogger(ActorServiceImpl.class);
    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public ActorCreateResponse createActor(ActorRequest request) {
        Actor actor = Actor.builder()
                .fullName(request.getFullName())
                .dateOfBirth(request.getDateOfBirth())
                .nationality(request.getNationality())
                .status(true)
                .build();
        Actor savedActor = actorRepository.save(actor);
        return new ActorCreateResponse(savedActor.getId(), savedActor.getFullName());
    }

    @Override
    public PageResponse getAllActors(Specification<Actor> spec, Pageable pageable) {
        Page<Actor> actorPage = this.actorRepository.findAll(spec, pageable);
        List<ActorResponse> actorList = actorPage.getContent()
                .stream()
                .map(actor -> ActorResponse.builder()
                        .id(actor.getId())
                        .fullName(actor.getFullName())
                        .dateOfBirth(actor.getDateOfBirth())
                        .nationality(actor.getNationality())
                        .status(actor.getStatus())
                        .build()
                ).toList();

        MetaPage metaPage = new MetaPage();
        metaPage.setPage(pageable.getPageNumber() + 1);
        metaPage.setPageSize(pageable.getPageSize());
        metaPage.setPages(actorPage.getTotalPages());
        metaPage.setTotal(actorPage.getTotalElements());
        return PageResponse.builder()
                .meta(metaPage)
                .result(actorList)
                .build();
    }
//
//    @Override
//    public PageResponse getAllActors(Pageable pageable) {
//        Page<Actor> actorPage = this.actorRepository.findAll(pageable);
//        List<ActorResponse> actorList = actorPage.getContent()
//                .stream()
//                .map(actor -> ActorResponse.builder()
//                        .id(actor.getId())
//                        .fullName(actor.getFullName())
//                        .dateOfBirth(actor.getDateOfBirth())
//                        .nationality(actor.getNationality())
//                        .status(actor.getStatus())
//                        .build()
//                ).toList();
//
//        MetaPage metaPage = new MetaPage();
//        metaPage.setPage(actorPage.getNumber() + 1);
//        metaPage.setPageSize(actorPage.getSize());
//        metaPage.setPages(actorPage.getTotalPages());
//        metaPage.setTotal(actorPage.getTotalElements());
//        return PageResponse.builder()
//                .meta(metaPage)
//                .result(actorList)
//                .build();
//    }

    @Override
    public ActorResponse getActorById(long actorId) {
        Actor actor = this.actorRepository.findById(actorId).orElseThrow(() -> new AppException(ErrorCode.ACTOR_NOT_EXISTED));
        return ActorResponse.builder()
                .id(actor.getId())
                .fullName(actor.getFullName())
                .dateOfBirth(actor.getDateOfBirth())
                .nationality(actor.getNationality())
                .status(actor.getStatus())
                .build();
    }

    @Override
    public void updateActor(long actorId, ActorRequest request) {
        Actor actor = this.actorRepository.findById(actorId).orElseThrow(() -> new AppException(ErrorCode.ACTOR_NOT_EXISTED));
        actor.setFullName(request.getFullName());
        actor.setDateOfBirth(request.getDateOfBirth());
        actor.setNationality(request.getNationality());
        actor.setStatus(request.getStatus());
        this.actorRepository.save(actor);

        log.info("Actor has updated successfully, actorId={}", actorId);
    }

    @Override
    public ActorResponse changeStatus(long actorId) {
        return null;
    }
}
