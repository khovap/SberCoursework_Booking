package com.github.khovap.coursework.bookingsource_main.service.implementation;

import com.github.khovap.coursework.bookingsource_main.entity.SpecialistEntity;
import com.github.khovap.coursework.bookingsource_main.mapper.SpecialistToEntityMapper;
import com.github.khovap.coursework.bookingsource_main.model.Specialist;
import com.github.khovap.coursework.bookingsource_main.repository.SpecialistRepository;
import com.github.khovap.coursework.bookingsource_main.service.SpecialistEntityService;
import com.github.khovap.coursework.bookingsource_main.service.exception.SpecialistNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultSpecialistService implements SpecialistEntityService {

    private final SpecialistRepository specialistRepository;
    private final SpecialistToEntityMapper mapper;

    @Override
    @SneakyThrows
    public Specialist getSpecialistById(long id) {
            SpecialistEntity Specialist = specialistRepository
                    .findById(id)
                    .orElseThrow(() -> new SpecialistNotFoundException("Specialist not found"));

            return mapper.specialistEntityToSpecialist(Specialist);
    }

    @Override
    public List<Specialist> getAllSpecialists() {
        Iterable<SpecialistEntity> SpecialistEntities = specialistRepository.findAll();
        
        ArrayList<Specialist> Specialists = new ArrayList<>();
        for (SpecialistEntity a : SpecialistEntities) {
            Specialists.add(mapper.specialistEntityToSpecialist(a));
        }
        return Specialists;
    }

    @Override
    public List<Specialist> getSpecialistsByMedicalServiceId(Long id) {
        Iterable<SpecialistEntity> specialistEntities = specialistRepository
                .findSpecialistEntitiesByMedicalServiceId(id);

        List<Specialist> specialists = new ArrayList<>();
        for (SpecialistEntity a : specialistEntities) {
            specialists.add(mapper.specialistEntityToSpecialist(a));
        }
        specialists = specialists.stream().sorted().collect(Collectors.toList());
        return specialists;
    }

    @Override
    public Specialist getSpecialistByPhoneNumber(String phoneNumber){
        SpecialistEntity specialistEntity = specialistRepository.findSpecialistEntitiesByPhoneNumberEquals(phoneNumber);
        return  mapper.specialistEntityToSpecialist(specialistEntity);
    }

}
