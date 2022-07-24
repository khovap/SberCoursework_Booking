package com.github.khovap.coursework.bookingsource_main.service;

import com.github.khovap.coursework.bookingsource_main.entity.AppointmentEntity;
import com.github.khovap.coursework.bookingsource_main.model.Appointment;
import com.github.khovap.coursework.bookingsource_main.service.exception.AppointmentAlreadyOccupiedException;
import com.github.khovap.coursework.bookingsource_main.service.exception.AppointmentNotFoundException;
import com.github.khovap.coursework.bookingsource_main.service.exception.ClientNotFoundException;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public interface AppointmentEntityService {
    Appointment getAppointmentById(long id) throws Throwable;

    List<Appointment> getAllAppointmentByClient(long id);

    List<Appointment> getOccupiedAppointmentBySpecialist(long id);

    List<Appointment> getAllAppointments();
    List<Appointment> getVacantAppointmentOfSpecialistByIdOnCurrentDate(Long specId, Date date);
    void addAppointment(Appointment appointment);
    void addAllAppointments(List<AppointmentEntity> appointmentEntities);
    void updateAppointment(long appointmentId, long clientId, boolean paid)
            throws AppointmentNotFoundException, ClientNotFoundException, AppointmentAlreadyOccupiedException;
    @SneakyThrows
    void updateAppointmentCancel(long appointmentId) throws AppointmentNotFoundException, ClientNotFoundException;

    @Scheduled(cron = "0 1 00 00 * ?", zone = "Europe/Moscow")
    List<AppointmentEntity> createListOfACurrentDayAppointmentTimetable();

    void createAppointmentsTimetableNextMonth();


    List<AppointmentEntity> createListOfADayAppointmentTimetable(LocalDate date);
    void updateAppointmentsTimetable();
}
