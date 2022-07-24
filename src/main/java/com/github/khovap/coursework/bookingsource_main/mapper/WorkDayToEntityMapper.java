package com.github.khovap.coursework.bookingsource_main.mapper;

import com.github.khovap.coursework.bookingsource_main.entity.ClientEntity;
import com.github.khovap.coursework.bookingsource_main.entity.WorkDayEntity;
import com.github.khovap.coursework.bookingsource_main.model.Client;
import com.github.khovap.coursework.bookingsource_main.model.DayOfWeekRu;
import com.github.khovap.coursework.bookingsource_main.model.WorkDay;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;


@Component
@Mapper(componentModel = "spring")
public interface WorkDayToEntityMapper {

    @Named("WorkDayToWorkDayEntity")
    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    WorkDayEntity WorkDayToWorkDayEntity(WorkDay workDay);

    @Named("WorkDayEntityToWorkDay")
    @Mapping(target = "dayOfWeek", source = "dayOfWeek")
    WorkDay WorkDayEntityToWorkDay(WorkDayEntity workDay);

//    @IterableMapping(qualifiedByName = "WorkDayEntityToWorkDay")
//    @Named("WorkDayEntityListToWorkDay")
//    List<WorkDay> WorkDayEntityListToWorkDay(List<WorkDayEntity> workDayEntity);
//
//    @IterableMapping(qualifiedByName = "WorkDayToWorkDayEntity")
//    @Named("WorkDayListToWorkDayEntity")
//    List<WorkDayEntity> WorkDayListToWorkDayEntity (List<WorkDay> workDay);

}
