package com.github.khovap.coursework.bookingsource_main.repository;

import com.github.khovap.coursework.bookingsource_main.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
    List<ClientEntity> findAllByNameContaining(String name);

    ClientEntity findByPhoneNumberEquals(String phoneNumber);
}
