package com.github.khovap.coursework.bookingsource_main.model;

import com.github.khovap.coursework.bookingsource_main.entity.SpecialistEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
public class WorkDay {

    private Long Id;

    private LocalTime SpecialistAppointmentStart;

    private LocalTime SpecialistAppointmentEnd;

    private DayOfWeek dayOfWeek;

    private SpecialistEntity specialist;


}
