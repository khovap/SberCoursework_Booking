package com.github.khovap.coursework.bookingsource_main.repository;

import com.github.khovap.coursework.bookingsource_main.entity.ClientEntity;
import com.github.khovap.coursework.bookingsource_main.entity.SpecialistEntity;
import com.github.khovap.coursework.bookingsource_main.model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistEntity, Long> {
//    List<SpecialistEntity> findAllByMedicalServiceContaining(Lo)
//@Query( "SELECT spec FROM SpecialistEntity spec WHERE spec.medicalService =:msIds")
    List<SpecialistEntity> findSpecialistEntitiesByMedicalServiceId(@Param("msIds") Long id);

    SpecialistEntity findSpecialistEntitiesByPhoneNumberEquals(String phoneNumber);
}
