package com.lvv.ttimpex2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lvv.ttimpex2.molel.Events;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TimeStampLastDto {
    @DateTimeFormat(pattern = "HH:mm:ss", iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime time;
    private Events event;
}
