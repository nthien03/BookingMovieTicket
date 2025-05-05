package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.ScheduleRequest;
import com.example.booking_movie_ticket.dto.response.MovieListResponse;
import com.example.booking_movie_ticket.dto.response.ScheduleCreateResponse;
import com.example.booking_movie_ticket.dto.response.ScheduleListResponse;
import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.entity.Room;
import com.example.booking_movie_ticket.entity.Schedule;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.exception.ResourceNotFoundException;
import com.example.booking_movie_ticket.repository.MovieRepository;
import com.example.booking_movie_ticket.repository.RoomRepository;
import com.example.booking_movie_ticket.repository.ScheduleRepository;
import com.example.booking_movie_ticket.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, MovieRepository movieRepository, RoomRepository roomRepository) {
        this.scheduleRepository = scheduleRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void updateSchedule(Long scheduleId, ScheduleRequest request) {
//        Schedule schedule = scheduleRepository.findById(scheduleId)
//                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + scheduleId));
//
//        Movie movie = movieRepository.findById(request.getMovieId())
//                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + request.getMovieId()));
//
//        Room room = roomRepository.findById(request.getRoomId())
//                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.getRoomId()));
//
//        schedule.setMovie(movie);
//        schedule.setRoom(room);
//        schedule.setDate(request.getDate());
//        schedule.setStartTime(request.getStartTime());
//        schedule.setEndTime(request.getEndTime());
//        schedule.setStatus(request.getStatus());
//
//        scheduleRepository.save(schedule);
    }

    //    @Override
//    public List<ScheduleListResponse> getAllSchedules() {
//        List<Schedule> schedules = scheduleRepository.findAll();
//        return schedules.stream()
//                .map(schedule -> new ScheduleListResponse(
//                        schedule.getId(),
//                        schedules.get
//                        movie.getPoster(),
//                        movie.getDuration(),
//                        movie.getReleaseDate()))
//                .collect(Collectors.toList());
//    }
    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }


    @Override
    public ScheduleCreateResponse createSchedule(ScheduleRequest request) {
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with id: " + request.getMovieId()));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + request.getRoomId()));

        // Check trùng phòng và thời gian
        boolean isConflict = scheduleRepository.existsByRoomAndDateAndTimeOverlap(
                room.getId(),
                request.getDate(),
                request.getStartTime(),
                request.getEndTime()
        );

        if (isConflict) {
            //throw new RuntimeException("Phòng chiếu đã được sử dụng vào thời gian bạn chọn");
            throw new AppException(ErrorCode.ROOM_NOT_FREE);
        }

        Schedule schedule = Schedule.builder()
                .movie(movie)
                .room(room)
                .date(request.getDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .status(request.getStatus())
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleCreateResponse(savedSchedule.getId());
    }
}
