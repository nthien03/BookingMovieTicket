package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.ScheduleRequest;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.room.RoomListResponse;
import com.example.booking_movie_ticket.dto.response.schedule.ScheduleByMovieResponse;
import com.example.booking_movie_ticket.dto.response.schedule.ScheduleCreateResponse;
import com.example.booking_movie_ticket.dto.response.schedule.ScheduleListResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatDetailResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_EXISTED));

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

        // Check trùng phòng và thời gian
        boolean isConflict = scheduleRepository.existsByRoomAndDateAndTimeOverlapExceptId(
                room.getId(),
                request.getDate(),
                request.getStartTime(),
                request.getEndTime(),
                scheduleId
        );

        if (isConflict) {
            //throw new RuntimeException("Phòng chiếu đã được sử dụng vào thời gian bạn chọn");
            throw new AppException(ErrorCode.ROOM_NOT_FREE);
        }
        schedule.setMovie(movie);
        schedule.setRoom(room);
        schedule.setDate(request.getDate());
        schedule.setStartTime(request.getStartTime());
        schedule.setBufferTime(request.getBufferTime());
        schedule.setEndTime(request.getEndTime());
        schedule.setStatus(request.getStatus());

        scheduleRepository.save(schedule);
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
    public PageResponse getSchedules(Instant date, String movieName, Pageable pageable) {
        Page<Schedule> schedulePage = scheduleRepository.findSchedulesByDateAndMovieName(date, movieName, pageable);

        List<ScheduleListResponse> schList = schedulePage.getContent()
                .stream().map(
                        schedule -> ScheduleListResponse.builder()
                                .id(schedule.getId())
                                .movie(new ScheduleListResponse.MovieInSchedule(
                                        schedule.getMovie().getId(),
                                        schedule.getMovie().getMovieName(),
                                        schedule.getMovie().getDuration()))
                                .room(new ScheduleListResponse.RoomInSchedule(schedule.getRoom().getId(),
                                        schedule.getRoom().getRoomName()))
                                .date(schedule.getDate())
                                .startTime(schedule.getStartTime())
                                .bufferTime(schedule.getBufferTime())
                                .endTime(schedule.getEndTime())
                                .status(schedule.getStatus())
                                .build()
                ).toList();
        MetaPage meta = MetaPage.builder()
                .page(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .pages(schedulePage.getTotalPages())
                .total(schedulePage.getTotalElements())
                .build();

        return PageResponse.builder()
                .meta(meta)
                .result(schList)
                .build();
    }


    @Override
    public ScheduleCreateResponse createSchedule(ScheduleRequest request) {
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new AppException(ErrorCode.MOVIE_NOT_EXISTED));

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
                .bufferTime(request.getBufferTime())
                .endTime(request.getEndTime())
                .status(request.getStatus())
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleCreateResponse(savedSchedule.getId());
    }

    @Override
    public ScheduleListResponse getScheduleById(long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_EXISTED));

        return ScheduleListResponse.builder()
                .id(schedule.getId())
                .movie(new ScheduleListResponse.MovieInSchedule(
                        schedule.getMovie().getId(),
                        schedule.getMovie().getMovieName(),
                        schedule.getMovie().getDuration()))
                .room(new ScheduleListResponse.RoomInSchedule(schedule.getRoom().getId(),
                schedule.getRoom().getRoomName()))
                .date(schedule.getDate())
                .startTime(schedule.getStartTime())
                .bufferTime(schedule.getBufferTime())
                .endTime(schedule.getEndTime())
                .status(schedule.getStatus())
                .build();

    }


    @Override
    public List<RoomListResponse> getAvailableRooms(Instant startTime, Instant endTime) {
        List<Long> busyRoomIds = scheduleRepository.findRoomIdsWithScheduleConflict(startTime, endTime);

        List<Room> availableRooms = busyRoomIds.isEmpty()
                ? roomRepository.findAll()
                : roomRepository.findByIdNotIn(busyRoomIds);

        return availableRooms.stream()
                .map(room -> new RoomListResponse(room.getId(), room.getRoomName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleByMovieResponse> getSchedulesByMovieId(Long movieId) {
        ZoneId vnZone = ZoneId.of("Asia/Ho_Chi_Minh");

        // Lấy đầu ngày hôm nay ở VN (00:00)
        ZonedDateTime vnStartOfToday = LocalDate.now(vnZone).atStartOfDay(vnZone);

        // Chuyển thành Instant (UTC)
        Instant startOfTodayInstant = vnStartOfToday.toInstant();

        Instant now = Instant.now();

        List<Schedule> schedules = scheduleRepository.findByMovieIdAndStatusTrueAndStartTimeGreaterThanEqual(movieId, now);
        return schedules.stream().map(schedule -> new ScheduleByMovieResponse(
                schedule.getId(),
                new ScheduleByMovieResponse.RoomInSchedule(
                        schedule.getRoom().getId(),
                        schedule.getRoom().getRoomName()
                ),
                schedule.getDate(),
                schedule.getStartTime()
        )).toList();
    }


}


