package com.github.khovap.coursework.bookingsource_main.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class WorkDayEntity {
    @Id
    @GeneratedValue
    private Long Id;

    @Column
    private LocalTime SpecialistAppointmentStart;

    @Column
    private LocalTime SpecialistAppointmentEnd;

    @Column
    private DayOfWeek dayOfWeek;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private SpecialistEntity specialist;


}
