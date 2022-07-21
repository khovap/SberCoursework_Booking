package com.github.khovap.coursework.bookingsource_main.mapper;

import com.github.khovap.coursework.bookingsource_main.entity.AppointmentEntity;
import com.github.khovap.coursework.bookingsource_main.model.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentToEntityMapper {

    @Mapping(target = "medicalServiceId", source = "appointmentEntity.specialist.medicalService.id")
    @Mapping(target = "medicalServiceName", source = "appointmentEntity.specialist.medicalService.name")
    @Mapping(target = "specialistId", source = "appointmentEntity.specialist.id")
    @Mapping(target = "specialistName",
            expression = "java(appointmentEntity.getSpecialist().getSurname() +" +
                    " \" \" + appointmentEntity.getSpecialist().getName() +" +
            " \" \" + appointmentEntity.getSpecialist().getPatronymic())")
    @Mapping(target = "specialistPatronymic", source = "appointmentEntity.specialist.patronymic")
    @Mapping(target = "specialistSurname", source = "appointmentEntity.specialist.surname")
    @Mapping(target = "clientId", source = "appointmentEntity.client.id")
    @Mapping(target = "clientName", source = "appointmentEntity.client.name")
    @Mapping(target = "clientSurname", source = "appointmentEntity.client.surname")
    @Mapping(target = "clientPatronymic", source = "appointmentEntity.client.patronymic")
    Appointment appointmentEntityToAppointment(AppointmentEntity appointmentEntity);

    AppointmentEntity appointmentToAppointmentEntity(Appointment appointment);
}
