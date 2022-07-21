package com.github.khovap.coursework.bookingsource_main.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clients")
public class ClientEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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
    private String healthInsurancePolicyNumber;

    @OneToMany(mappedBy = "client")
    private List<AppointmentEntity> appointments;

    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic;
    }
}
