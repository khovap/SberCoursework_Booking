package com.github.khovap.coursework.bookingsource_main.repository;

import com.github.khovap.coursework.bookingsource_main.entity.MedicalServiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalServiceRepository extends CrudRepository<MedicalServiceEntity, Long> {
}
