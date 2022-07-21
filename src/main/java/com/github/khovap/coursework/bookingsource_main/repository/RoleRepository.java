package com.github.khovap.coursework.bookingsource_main.repository;

import com.github.khovap.coursework.bookingsource_main.entity.SpecialistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<SpecialistEntity, Long> {
}
