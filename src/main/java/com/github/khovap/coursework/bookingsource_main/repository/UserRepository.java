package com.github.khovap.coursework.bookingsource_main.repository;

import com.github.khovap.coursework.bookingsource_main.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    List<UserEntity> findAll();
}
