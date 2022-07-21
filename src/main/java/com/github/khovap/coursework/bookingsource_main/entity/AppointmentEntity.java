package com.github.khovap.coursework.bookingsource_main.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointments")
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private Date date;

    @Column
    private Time time;

    @Column
    private boolean paid;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client ;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private SpecialistEntity specialist;
}
