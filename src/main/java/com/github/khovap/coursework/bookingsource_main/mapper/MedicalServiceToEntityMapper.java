package com.github.khovap.coursework.bookingsource_main.mapper;

import com.github.khovap.coursework.bookingsource_main.entity.MedicalServiceEntity;
import com.github.khovap.coursework.bookingsource_main.model.MedicalService;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface MedicalServiceToEntityMapper {
    MedicalServiceEntity medicalServiceToMedicalServiceEntity(MedicalService medical);
    MedicalService medicalServiceEntityToMedicalService(MedicalServiceEntity medicalServiceEntity);
}
