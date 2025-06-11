package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    boolean existsByRoomName(String name);

    List<Room> findByIdNotIn(List<Long> ids);

    Optional<Room> findByRoomName(String roomName);
}
