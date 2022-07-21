package com.github.khovap.coursework.bookingsource_main.service;

import com.github.khovap.coursework.bookingsource_main.model.Specialist;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SpecialistEntityService {
    Specialist getSpecialistById(long id);
    List<Specialist> getAllSpecialists();

    List<Specialist> getSpecialistsByMedicalServiceId(Long id);

    Specialist getSpecialistByPhoneNumber(String phoneNumber);

//    void addSpecialist(Specialist specialist);

}
