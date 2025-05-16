package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.ActorRequest;
import com.example.booking_movie_ticket.dto.response.ActorCreateResponse;
import com.example.booking_movie_ticket.dto.response.ActorResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.entity.Actor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface ActorService {
    ActorCreateResponse createActor(ActorRequest request);
    //PageResponse getAllActors(Pageable pageable);
    PageResponse getAllActors(Specification<Actor> spec, Pageable pageable);
    ActorResponse getActorById(long actorId);
    void updateActor(long actorId, ActorRequest request);
    ActorResponse changeStatus(long actorId);

}
