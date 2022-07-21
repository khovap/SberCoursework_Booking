package com.github.khovap.coursework.bookingsource_main.service.implementation;

import com.github.khovap.coursework.bookingsource_main.entity.WorkDayEntity;
import com.github.khovap.coursework.bookingsource_main.mapper.WorkDayToEntityMapper;
import com.github.khovap.coursework.bookingsource_main.model.WorkDay;
import com.github.khovap.coursework.bookingsource_main.repository.WorkDayRepository;
import com.github.khovap.coursework.bookingsource_main.service.WorkDayEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultWorkDayEntityService implements WorkDayEntityService {

    private final WorkDayRepository workDayRepository;
    private final WorkDayToEntityMapper mapper;

    @Override
    public List<WorkDay> getAllWorkDaysBySpecialist(Long id) {
        Iterable<WorkDayEntity> workDayEntities = workDayRepository.findAllBySpecialistId(id);
        ArrayList<WorkDay> workDays = new ArrayList<>();
        for (WorkDayEntity wd : workDayEntities){
            workDays.add(mapper.WorkDayEntityToWorkDay(wd));
        }
        return workDays;
    }
}
