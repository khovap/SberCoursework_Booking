package com.github.khovap.coursework.bookingsource_main.service.implementation;

import com.github.khovap.coursework.bookingsource_main.entity.ClientEntity;
import com.github.khovap.coursework.bookingsource_main.entity.SpecialistEntity;
import com.github.khovap.coursework.bookingsource_main.entity.WorkDayEntity;
import com.github.khovap.coursework.bookingsource_main.model.Appointment;
import com.github.khovap.coursework.bookingsource_main.repository.AppointmentRepository;
import com.github.khovap.coursework.bookingsource_main.entity.AppointmentEntity;
import com.github.khovap.coursework.bookingsource_main.repository.ClientRepository;
import com.github.khovap.coursework.bookingsource_main.repository.SpecialistRepository;
import com.github.khovap.coursework.bookingsource_main.service.AppointmentEntityService;
import com.github.khovap.coursework.bookingsource_main.service.exception.AppointmentAlreadyOccupiedException;
import com.github.khovap.coursework.bookingsource_main.service.exception.AppointmentNotFoundException;
import com.github.khovap.coursework.bookingsource_main.service.exception.ClientNotFoundException;
import com.github.khovap.coursework.bookingsource_main.mapper.AppointmentToEntityMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultAppointmentService implements AppointmentEntityService {

//    private final KafkaTemplate<Long, AppointmentStatDTO> kafkaTemplate;
//    private final AppointmentToDTOMapper appointmentToDTOMapper;
    private final AppointmentRepository appointmentRepository;
    private final SpecialistRepository specialistRepository;
    private final ClientRepository clientRepository;
    private final AppointmentToEntityMapper mapper;

    @Override
    public Appointment getAppointmentById(long id) throws AppointmentNotFoundException {
        AppointmentEntity appointmentEntity = appointmentRepository
                .findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found"));
        return mapper.appointmentEntityToAppointment(appointmentEntity);
    }

    @Override
    public List<Appointment> getAllAppointmentByClient(long id){
        List<AppointmentEntity> appointmentEntities = appointmentRepository
                .findAppointmentEntitiesByClientId(id);
        List<Appointment> appointments = new ArrayList<>();
        for (AppointmentEntity ae: appointmentEntities) {
            appointments.add(mapper.appointmentEntityToAppointment(ae));
        }
        return appointments;
    }

    @Override
    public List<Appointment> getOccupiedAppointmentBySpecialist(long id){
        List<AppointmentEntity> appointmentEntities = appointmentRepository
                .findAppointmentEntitiesBySpecialistIdAndClientNotNull(id);
        List<Appointment> appointments = new ArrayList<>();
        for (AppointmentEntity ae: appointmentEntities) {
            appointments.add(mapper.appointmentEntityToAppointment(ae));
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        Iterable<AppointmentEntity> appointmentEntities = appointmentRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id"));

        ArrayList<Appointment> appointments = new ArrayList<>();
        for (AppointmentEntity a : appointmentEntities) {
            appointments.add(mapper.appointmentEntityToAppointment(a));
        }
        return appointments;
    }

    @Override
    public List<Appointment> getVacantAppointmentOfSpecialistByIdOnCurrentDate(Long specId, Date date){
        Iterable<AppointmentEntity> appointmentsEntities = appointmentRepository
                .findAppointmentEntitiesBySpecialistIdAndDateAndClientIsNull(specId, date);
        ArrayList<Appointment> appointments = new ArrayList<>();
        for (AppointmentEntity a : appointmentsEntities){
            appointments.add(mapper.appointmentEntityToAppointment(a));
        }
        return appointments;
    }

    @Override
    public void addAppointment(Appointment appointment) {
        AppointmentEntity appointmentEntity =
                mapper.appointmentToAppointmentEntity(appointment);
        appointmentRepository.save(appointmentEntity);
    }

    @Override
    public void addAllAppointments(List<AppointmentEntity> appointmentEntities) {
        appointmentRepository.saveAll(appointmentEntities);
    }

    @Override
    public void updateAppointmentsTimetable() {
        List<AppointmentEntity> appointmentEntities;
        appointmentEntities = createListOfADayAppointmentTimetable(LocalDate.now()
                .plusMonths(1));
        addAllAppointments(appointmentEntities);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateAppointment(long appointmentId, long clientId, boolean paid)
            throws AppointmentNotFoundException, ClientNotFoundException, AppointmentAlreadyOccupiedException {
        AppointmentEntity appointmentEntity  = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(() ->new AppointmentNotFoundException("Запись на прием специалиста в данное время не возможна"));
        ClientEntity clientEntity  = clientRepository
                .findById(clientId)
                .orElseThrow(() ->new ClientNotFoundException("Client not found"));
        if (appointmentEntity.getClient() != null)
            throw new AppointmentAlreadyOccupiedException("Сеанс приема в данное время занят");
        appointmentEntity.setClient(clientEntity);
        appointmentEntity.setPaid(paid);
//        kafkaTemplate.send("msg", appointmentToDTOMapper
//                .appointmentEntityToDTO(appointmentEntity));
    }

    @Override
    public void updateAppointmentCancel(long appointmentId) throws AppointmentNotFoundException {
        AppointmentEntity appointmentEntity  = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(() ->new AppointmentNotFoundException("Appointment not found"));
        appointmentEntity.setClient(null);
        appointmentEntity.setPaid(false);
        appointmentRepository.save(appointmentEntity);
    }

    @Override
    public List<AppointmentEntity> createListOfADayAppointmentTimetable(LocalDate date) {
        if (date == null) throw new NullPointerException("Date should not be null");
        Iterable<SpecialistEntity> allSpecialists = specialistRepository.findAll(
                Sort.by(Sort.Direction.ASC, "surname"));
        List<AppointmentEntity> appointmentEntities = new ArrayList<>();

        for (SpecialistEntity spec : allSpecialists) {
            HashMap<String,LocalTime> workTime= getSpecialistWorkTimeThisDay(spec, date);
            if(!workTime.isEmpty()){
                LocalTime startTime = workTime.get("start");
                LocalTime endTime = workTime.get("end");

                Duration duration;
                LocalTime time = startTime;
                while (time.isBefore(endTime)) {
                    AppointmentEntity appointmentEntity = new AppointmentEntity();
                    appointmentEntity.setSpecialist(spec);
                    appointmentEntity.setDate(Date.valueOf(date));
                    appointmentEntity.setTime(Time.valueOf(time));
                    appointmentEntities.add(appointmentEntity);
                    duration = Duration.between(LocalTime.MIDNIGHT, spec.getMedicalService().getDuration().toLocalTime());
                    time = time.plus(duration);
                }
            }
        }
        return appointmentEntities;
    }

    public HashMap<String, LocalTime> getSpecialistWorkTimeThisDay(SpecialistEntity specialist, LocalDate date){
        HashMap<String, LocalTime> workTime = new HashMap<>();
        List<WorkDayEntity> workDays = specialist.getWorkDays();
        DayOfWeek currentDay = date.getDayOfWeek();
        for (WorkDayEntity wd:workDays) {
            if (wd.getDayOfWeek().equals(currentDay)){
                workTime.put("start", wd.getSpecialistAppointmentStart());
                workTime.put("end", wd.getSpecialistAppointmentEnd());
            }
        }
        return workTime;
    }

    @Override
    @Scheduled(cron = "01 01 00 * * ?", zone = "Europe/Moscow")
    public List<AppointmentEntity> createListOfACurrentDayAppointmentTimetable() {
        LocalDate date = LocalDate.now();
        return createListOfADayAppointmentTimetable(date);
    }

    @Override
    public void createAppointmentsTimetableNextMonth() {
        if (!appointmentRepository.existsAppointmentEntitiesByDate(
                Date.valueOf(LocalDate.now().plusDays(1).toString()))) {
            LocalDate date = LocalDate.now();
            List<AppointmentEntity> appointmentEntities;
            while (date.isBefore(LocalDate.now().plusMonths(1))) {
                appointmentEntities = createListOfADayAppointmentTimetable(date);
                date = date.plusDays(1);
                addAllAppointments(appointmentEntities);
            }
        }
    }
}
