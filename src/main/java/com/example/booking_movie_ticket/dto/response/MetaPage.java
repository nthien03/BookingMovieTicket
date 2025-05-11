package com.example.booking_movie_ticket.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaPage {
    private int page;
    private int pageSize;
    private int pages;
    private long total;

}
