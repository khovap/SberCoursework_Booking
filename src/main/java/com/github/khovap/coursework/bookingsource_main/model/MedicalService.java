package com.github.khovap.coursework.bookingsource_main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Time;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MedicalService {

    private long id;
    private String name;
    private Time duration;
    private int price;
}
