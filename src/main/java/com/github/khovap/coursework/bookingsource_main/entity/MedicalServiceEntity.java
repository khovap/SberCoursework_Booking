package com.github.khovap.coursework.bookingsource_main.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medical_service")
public class MedicalServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private Time duration;

    @Column
    private int price;

    @OneToMany(mappedBy = "medicalService",cascade = CascadeType.ALL)
    List<SpecialistEntity> specialists;

    @Override
    public String toString() {
        return name;
    }
}
