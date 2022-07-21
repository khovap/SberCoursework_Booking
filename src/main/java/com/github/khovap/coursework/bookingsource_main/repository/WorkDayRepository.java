package com.github.khovap.coursework.bookingsource_main.repository;

import com.github.khovap.coursework.bookingsource_main.entity.ClientEntity;
import com.github.khovap.coursework.bookingsource_main.entity.WorkDayEntity;
import com.github.khovap.coursework.bookingsource_main.model.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface WorkDayRepository extends JpaRepository<WorkDayEntity, Long> {
    List<WorkDayEntity> findAllBySpecialistId(Long id);

}
