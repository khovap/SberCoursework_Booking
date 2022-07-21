package com.github.khovap.coursework.bookingsource_main.service.implementation;

import com.github.khovap.coursework.bookingsource_main.entity.AppointmentEntity;
import com.github.khovap.coursework.bookingsource_main.entity.MedicalServiceEntity;
import com.github.khovap.coursework.bookingsource_main.mapper.MedicalServiceToEntityMapper;
import com.github.khovap.coursework.bookingsource_main.model.Appointment;
import com.github.khovap.coursework.bookingsource_main.model.MedicalService;
import com.github.khovap.coursework.bookingsource_main.repository.MedicalServiceRepository;
import com.github.khovap.coursework.bookingsource_main.service.AppointmentEntityService;
import com.github.khovap.coursework.bookingsource_main.service.MedicalServiceEntityService;
import com.github.khovap.coursework.bookingsource_main.service.exception.MedicalServiceNotFoundException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultMedicalService implements MedicalServiceEntityService {

    private final MedicalServiceRepository medicalServiceRepository;
    private final MedicalServiceToEntityMapper mapper;

    public DefaultMedicalService(MedicalServiceRepository medicalServiceRepository, MedicalServiceToEntityMapper mapper) {
        this.medicalServiceRepository = medicalServiceRepository;
        this.mapper = mapper;
    }

    @Override
    @SneakyThrows
    public MedicalService getMedicalServiceById(long id){
            MedicalServiceEntity medicalService = medicalServiceRepository
                    .findById(id)
                    .orElseThrow(() -> new MedicalServiceNotFoundException("Medical service not found"));

            return mapper.medicalServiceEntityToMedicalService(medicalService);
    }

    @Override
    public List<MedicalService> getAllMedicalServices() {
        Iterable<MedicalServiceEntity>  medicalServiceEntities = medicalServiceRepository.findAll();
        
        ArrayList<MedicalService> medicalServices = new ArrayList<>();

        for (MedicalServiceEntity ms : medicalServiceEntities) {
            medicalServices.add(mapper.medicalServiceEntityToMedicalService(ms));
        }
        return medicalServices;
    }

    @Override
    public void addMedicalService(MedicalService medicalService) {
        MedicalServiceEntity medicalServiceEntity =
                mapper.medicalServiceToMedicalServiceEntity(medicalService);
        medicalServiceRepository.save(medicalServiceEntity);
    }
}
