package com.lvv.ttimpex2.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateSettingDto {
    @NotNull(message = "Setting start time can't be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;
    @NotNull(message = "Setting end time can't be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;
    @NotNull(message = "Setting penalty being late can't be null")
    @Positive
    private Integer penaltyBeingLate;
    @NotNull(message = "Setting penalty early care can't be null")
    @Positive
    private Integer penaltyEarlyCare;
    @NotNull(message = "Setting penalty for missing timestamp can't be null")
    @Positive
    private Integer penaltyForMissingTimestamp;
    @NotNull(message = "Setting penalty absenteeism can't be null")
    @Positive
    private Integer penaltyAbsenteeism;
}
