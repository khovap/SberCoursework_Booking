package com.github.khovap.coursework.bookingsource_main.repository;

import com.github.khovap.coursework.bookingsource_main.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    boolean existsAppointmentEntitiesByDate(Date date);

    List<AppointmentEntity> findAppointmentEntitiesBySpecialistIdAndDateAndClientIsNull(Long specId, Date date);

    List<AppointmentEntity> findAppointmentEntitiesByClientId(Long id);

    List<AppointmentEntity> findAppointmentEntitiesBySpecialistIdAndClientNotNull(Long id);



}
