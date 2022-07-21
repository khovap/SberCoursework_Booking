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
    private List<Specialist> specialists = new ArrayList<>();

    private Appointment appointment = new Appointment();


    @GetMapping("/reservation")
    public String getMedicalService(Model model){
        if (allMedicalServices.isEmpty())
            allMedicalServices = medicalServiceService.getAllMedicalServices();
        model.addAttribute("allMedicalServices", allMedicalServices);
        model.addAttribute("specialists", specialists);
        return "/appointment/reservation";
    }

    @GetMapping("/chosenMedicalService")
        public String chooseMedicalService(@RequestParam("chosenMedicalService") Long id,
                                           Model model){
        if (id != null)
            specialists = specialistService.getSpecialistsByMedicalServiceId(id);
        model.addAttribute("allMedicalServices", allMedicalServices);
        model.addAttribute("specialists", specialists);
        return "appointment/reservation";
    }

    @GetMapping("/chosenSpecialist")
    @SneakyThrows
    public String chooseSpecialist(@RequestParam("chosenSpecialist") Long id, Model model){
        if (id != null)
            appointment.setSpecialistId(id);
            model.addAttribute("allMedicalServices", allMedicalServices);
            model.addAttribute("specialists", specialists);
        return "redirect:/appointment/reservationDate";
    }

    @GetMapping("/reservationDate")
    @SneakyThrows
    public String getAppointmentDate(Model model){
        long specId = appointment.getSpecialistId();
        Date date = appointment.getDate();
        String specName = null;
        List<Appointment> specialistsCurrentDate = appointmentService.getVacantAppointmentOfSpecialistByIdOnCurrentDate(specId, date);
//        if (!specialistsCurrentDate.isEmpty())
//             specName = specialistsCurrentDate.get(0).getSpecialistName();
        model.addAttribute("specialistsCurrentDate",
                appointmentService.getVacantAppointmentOfSpecialistByIdOnCurrentDate(specId, date));
        model.addAttribute("textStyle", TextStyle.FULL);
        model.addAttribute("locale", Locale.forLanguageTag("ru"));
        return "/appointment/reservationDate";
    }

    @PostMapping("/chosenAppointmentDate")
    @SneakyThrows
    public String chooseTime(@RequestParam("appointmentDate") String date){
        System.out.println(date);
        appointment.setDate(Date.valueOf(date));
//        appointment.setDate(date);
        return "redirect:/appointment/reservationDate";
    }

    @PostMapping("/saveAppointment")
    @SneakyThrows
    public String chosenTime(@RequestParam("appointmentId") long appointmentId) {
        String userPhoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientService.getClientByPhoneNumber(userPhoneNumber);
        Long clientId = client.getId();
        appointment.setId(appointmentId);
        appointmentService.updateAppointment(appointment.getId(), clientId, true);
        return "redirect:/profile/";
    }
}
