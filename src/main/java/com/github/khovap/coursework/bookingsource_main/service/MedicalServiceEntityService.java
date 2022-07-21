package com.github.khovap.coursework.bookingsource_main.service;

import com.github.khovap.coursework.bookingsource_main.entity.MedicalServiceEntity;
import com.github.khovap.coursework.bookingsource_main.model.MedicalService;
import org.hibernate.engine.spi.ManagedEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MedicalServiceEntityService {
    MedicalService getMedicalServiceById(long id);
    List<MedicalService> getAllMedicalServices();
    void addMedicalService(MedicalService medicalService);

}
