package com.github.khovap.coursework.bookingsource_main.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "specialists")
public class SpecialistEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String patronymic;
    @Column
    private String surname;
    @Column
    private String phoneNumber;

    @Column
    private int room;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medical_service_id")
    private MedicalServiceEntity medicalService;

    @OneToMany(mappedBy = "specialist")
    private List<AppointmentEntity> appointments;

    @OneToMany(mappedBy = "specialist")
    private List<WorkDayEntity> workDays;

}
