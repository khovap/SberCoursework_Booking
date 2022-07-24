package com.github.khovap.coursework.bookingsource_main.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.sql.Time;
import java.sql.Date;


@Data
@AllArgsConstructor
@Component
@Builder
public class Appointment {
    private long id;

    private Date date;

    private Time time;

    private boolean paid;

    private long medicalServiceId;

    private String medicalServiceName;

    private long specialistId ;

    private String specialistName ;

    private String specialistPatronymic ;

    private String specialistSurname ;

    private long clientId;

    private String clientName;

    private String clientPatronymic ;

    private String clientSurname ;

    public Appointment(){
    }
}
