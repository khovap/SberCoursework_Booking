package com.github.khovap.coursework.bookingsource_main.service.implementation;

import com.github.khovap.coursework.bookingsource_main.entity.*;
import com.github.khovap.coursework.bookingsource_main.mapper.AppointmentToEntityMapper;
import com.github.khovap.coursework.bookingsource_main.repository.AppointmentRepository;
import com.github.khovap.coursework.bookingsource_main.repository.ClientRepository;
import com.github.khovap.coursework.bookingsource_main.repository.SpecialistRepository;
import com.github.khovap.coursework.bookingsource_main.service.exception.AppointmentAlreadyOccupiedException;
import com.github.khovap.coursework.bookingsource_main.service.exception.AppointmentNotFoundException;
import com.github.khovap.coursework.bookingsource_main.service.exception.ClientNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DefaultAppointmentServiceTest {

    @Mock
    private SpecialistRepository specialistRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AppointmentToEntityMapper mapper;

    private DefaultAppointmentService appointmentService;

    public static List<SpecialistEntity> specsToday;
    public static List<SpecialistEntity> specsNotToday;
    public static SpecialistEntity specToday;
    public static SpecialistEntity specNotToday;

    public static AppointmentEntity appointmentEntity;

    public static ClientEntity clientEntity;

    public static LocalDate today = LocalDate.now();

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        appointmentService = new DefaultAppointmentService(appointmentRepository,
                specialistRepository,
                clientRepository,
                mapper);
    }

    @BeforeAll
    public static void prepareTestSpecialists(){
        WorkDayEntity workDayToday = WorkDayEntity.builder()
                .dayOfWeek(LocalDate.now().getDayOfWeek())
                .SpecialistAppointmentStart(LocalTime.of(8,0))
                .SpecialistAppointmentEnd(LocalTime.of(14,0))
                .build();
        WorkDayEntity workDayNotToday = WorkDayEntity.builder()
                .dayOfWeek(LocalDate.now().plusDays(1).getDayOfWeek())
                .SpecialistAppointmentStart(LocalTime.of(14,0))
                .SpecialistAppointmentEnd(LocalTime.of(18,0))
                .build();
        WorkDayEntity workDay3 = WorkDayEntity.builder()
                .dayOfWeek(DayOfWeek.WEDNESDAY)
                .SpecialistAppointmentStart(LocalTime.of(16,0))
                .SpecialistAppointmentEnd(LocalTime.of(20,0))
                .build();

        List<WorkDayEntity> wdTodayList = new ArrayList<>();
        List<WorkDayEntity> wdNotTodayList = new ArrayList<>();
        wdTodayList.add(workDayToday);
        wdNotTodayList.add(workDayNotToday);

        MedicalServiceEntity ms1 = MedicalServiceEntity.builder()
                .duration(Time.valueOf("00:45:00"))
                .build();
        MedicalServiceEntity ms2 = MedicalServiceEntity.builder()
                .duration(Time.valueOf("00:30:00"))
                .build();
        MedicalServiceEntity ms3 = MedicalServiceEntity.builder()
                .duration(Time.valueOf("00:15:00"))
                .build();
        specToday = SpecialistEntity.builder()
                .surname("spec1")
                .medicalService(ms1)
                .workDays(wdTodayList)
                .build();
        specNotToday = SpecialistEntity.builder()
                .surname("spec2")
                .medicalService(ms1)
                .workDays(wdNotTodayList)
                .build();
        specsToday = Arrays.asList(specToday);
        specsNotToday = Arrays.asList(specNotToday);
        clientEntity = ClientEntity.builder().build();
        appointmentEntity = AppointmentEntity.builder()
                .client(clientEntity)
                .build();
    }

    @Test
    void isUpdateAppointmentsTimetableSaveAppointment(){
        appointmentService.updateAppointmentsTimetable();
        Mockito.verify(appointmentRepository).saveAll(Mockito.any());
    }

    @Test
    void isUpdateAppointmentThrowAppointmentException() {
        Mockito.when(appointmentRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.updateAppointment(1l,1l, true));
        Mockito.verify((appointmentRepository),Mockito.never()).save(Mockito.any());
    }

    @Test
    void isUpdateAppointmentThrowClientException() {
        Mockito.when(appointmentRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(appointmentEntity));
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        assertThrows(ClientNotFoundException.class, () -> appointmentService.updateAppointment(1l,1l, true));
        Mockito.verify((appointmentRepository),Mockito.never()).save(Mockito.any());
    }

    @Test
    void isUpdateAppointmentThrowAppointmentAllReadyOccupiedException() {
        Mockito.when(appointmentRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(appointmentEntity));
        Mockito.when(clientRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(clientEntity));
        assertThrows(AppointmentAlreadyOccupiedException.class,
                () -> appointmentService.updateAppointment(1l,1l, true));
        Mockito.verify((appointmentRepository),Mockito.never()).save(Mockito.any());
    }

    @Test
    void isUpdateAppointmentCancelSaveObject() {
        Mockito.when(appointmentRepository.findById(1l)).thenReturn(Optional.ofNullable(appointmentEntity));
        assertDoesNotThrow(() -> appointmentService.updateAppointmentCancel(1l));
        Mockito.verify(appointmentRepository).save(Mockito.any());
    }

    @Test
    void isUpdateAppointmentCancelThrowException() {
        Mockito.when(appointmentRepository.findById(1l)).thenReturn(Optional.ofNullable(null));
        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.updateAppointmentCancel(1l));
        Mockito.verify((appointmentRepository),Mockito.never()).save(Mockito.any());
    }

    @Test
    void createListOfADayAppTestIfNullDate() {
        assertThrows(NullPointerException.class,
                () -> appointmentService.createListOfADayAppointmentTimetable(null));
    }

    @Test
    void createListOfADayAppTestIfDateIsAWorkingDay() {
        Mockito.when(specialistRepository.findAll(
            Sort.by(Sort.Direction.ASC, "surname"))).thenReturn(specsToday);
        assertEquals(8,appointmentService.createListOfADayAppointmentTimetable(today).size());
    }

    @Test
    void createListOfADayAppTestIfDateNotWorkDay() {
        Mockito.when(specialistRepository.findAll(
                Sort.by(Sort.Direction.ASC, "surname"))).thenReturn(specsNotToday);
        assertEquals(0,appointmentService.createListOfADayAppointmentTimetable(today).size());
    }

    @Test
    void isGetSpecialistWorkTimeThisDayCreated(){
        assertTrue(appointmentService.getSpecialistWorkTimeThisDay(specToday,today)
                .get("start") == specToday.getWorkDays().get(0).getSpecialistAppointmentStart());
        assertTrue(appointmentService.getSpecialistWorkTimeThisDay(specToday,today)
                .get("end")== specToday.getWorkDays().get(0).getSpecialistAppointmentEnd());
    }

    @Test
    void isGetSpecialistWorkTimeThisDayNullWhenThereIsNotWorkDay(){
        assertTrue(appointmentService.getSpecialistWorkTimeThisDay(specNotToday,today).isEmpty());
    }

    @Test
    void isCreateAppointmentsTimetableNextMonthSaveAppointments() {
        Mockito.when(appointmentRepository.existsAppointmentEntitiesByDate(Mockito.any()))
                .thenReturn(false);
        Mockito.when(specialistRepository.findAll(
                        Sort.by(Sort.Direction.ASC, "surname"))).thenReturn(specsToday);
        assertDoesNotThrow(() -> appointmentService.createAppointmentsTimetableNextMonth());
        Mockito.verify((appointmentRepository),Mockito.atLeast(28)).saveAll(Mockito.any());
    }

    @Test
    void isCreateAppointmentsTimetableNextMonthDontSaveAppointmentsIfThemExists() {
        Mockito.when(appointmentRepository.existsAppointmentEntitiesByDate(Mockito.any()))
                .thenReturn(true);
        assertDoesNotThrow(() -> appointmentService.createAppointmentsTimetableNextMonth());
        Mockito.verify((appointmentRepository),Mockito.never()).saveAll(Mockito.any());
    }

}