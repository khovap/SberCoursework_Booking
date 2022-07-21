package com.github.khovap.coursework.bookingsource_main.service;

import com.github.khovap.coursework.bookingsource_main.model.Client;
import com.github.khovap.coursework.bookingsource_main.model.WorkDay;

import java.util.List;

public interface WorkDayEntityService {

    List<WorkDay> getAllWorkDaysBySpecialist(Long id);

}
