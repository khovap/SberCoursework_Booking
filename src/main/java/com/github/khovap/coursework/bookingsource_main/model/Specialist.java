package com.github.khovap.coursework.bookingsource_main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Specialist implements Comparable<Specialist>{

    private long id;

    private String name;

    private String surname;

    private String patronymic;

    private int room;

    private String phoneNumber;

    private long medicalServiceId;

    private String medicalServiceName;

    private List<Long> appointmentsId;

    private List<WorkDay> workDays;


    @Override
    public int compareTo(Specialist o) {
        if (this.surname.compareTo(o.surname) != 0)
            return this.surname.compareTo(o.surname);
        if (this.name.compareTo(o.name) != 0)
            return this.name.compareTo(o.name);
        if (this.patronymic.compareTo(o.patronymic) != 0)
            return this.patronymic.compareTo(o.patronymic);
        if (this.room != o.room)
            return this.room - o.room;
        return 0;
    }

    @Override
    public String toString() {
        return name + " " + patronymic + " " + surname + " кабинет №" + room;
    }
}
