package com.github.khovap.coursework.bookingsource_main.controller;


import com.github.khovap.coursework.bookingsource_main.model.Appointment;
import com.github.khovap.coursework.bookingsource_main.model.Client;
import com.github.khovap.coursework.bookingsource_main.model.MedicalService;
import com.github.khovap.coursework.bookingsource_main.model.Specialist;
import com.github.khovap.coursework.bookingsource_main.service.AppointmentEntityService;
import com.github.khovap.coursework.bookingsource_main.service.ClientEntityService;
import com.github.khovap.coursework.bookingsource_main.service.MedicalServiceEntityService;
import com.github.khovap.coursework.bookingsource_main.service.SpecialistEntityService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/appointment")
@RequiredArgsConstructor
@Validated
public class AppointmentController {
    private final AppointmentEntityService appointmentService;
    private final MedicalServiceEntityService medicalServiceService;
    private final SpecialistEntityService specialistService;
    private final ClientEntityService clientService;


    private List<MedicalService> allMedicalServices = new ArrayList<>();


    @GetMapping("/reservation")
    public String getMedicalServiceAndSpecialist(/*@PathVariable String userId,*/
                                                 @RequestParam(required = false, name = "msId") Long msId,
                                                 @RequestParam(required = false, name = "specId") Long specId,
                                                 Model model){
        List<Specialist> specialists = new ArrayList<>();
        if (msId == null) {
            allMedicalServices = medicalServiceService.getAllMedicalServices();
        } else {
            specialists = specialistService.getSpecialistsByMedicalServiceId(msId);
        }
        if (specId != null) {
            return "redirect:/appointment/reservationDate?specId=" + specId;
        }
        model.addAttribute("specialists", specialists);
        model.addAttribute("allMedicalServices", allMedicalServices);
        model.addAttribute("textStyle", TextStyle.FULL);
        model.addAttribute("locale", Locale.forLanguageTag("ru"));
        return "/appointment/reservation";
    }

    @GetMapping("/reservationDate")
    @SneakyThrows
    public String getAppointmentDate(@RequestParam(required = false, name = "specId") String specId,
                                     @RequestParam(required = false) String date,
                                     Model model){
//        long specId = appointment.getSpecialistId();
        List<Appointment> specialistsCurrentDate = new ArrayList<>();
      if (date != null) {
          specialistsCurrentDate = appointmentService
                  .getVacantAppointmentOfSpecialistByIdOnCurrentDate(Long.valueOf(specId), Date.valueOf(date));
      }
        model.addAttribute("specialistsCurrentDate", specialistsCurrentDate);
        model.addAttribute("specId", specId);
        return "/appointment/reservationDate";
    }

    @PostMapping("/saveAppointment")
    @SneakyThrows
    public String chosenTime(@RequestParam("appointmentId") long appointmentId) {
        String userPhoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByPhoneNumber(userPhoneNumber);
        Long clientId = client.getId();
        appointmentService.updateAppointment(appointmentId, clientId, true);
        return "redirect:/profile/";
    }
}
