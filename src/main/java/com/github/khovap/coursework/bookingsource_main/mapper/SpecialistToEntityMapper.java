package com.github.khovap.coursework.bookingsource_main.mapper;

import com.github.khovap.coursework.bookingsource_main.entity.SpecialistEntity;
import com.github.khovap.coursework.bookingsource_main.model.DayOfWeekRu;
import com.github.khovap.coursework.bookingsource_main.model.Specialist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface SpecialistToEntityMapper {

    @Mapping(target = "workDays", source = "workDays")
    SpecialistEntity SpecialistToSpecialistEntity(Specialist specialist);
    @Mapping(target = "medicalServiceId", source = "specialistEntity.medicalService.id")
    @Mapping(target = "medicalServiceName", source = "specialistEntity.medicalService.name")
    Specialist specialistEntityToSpecialist(SpecialistEntity specialistEntity);

}
