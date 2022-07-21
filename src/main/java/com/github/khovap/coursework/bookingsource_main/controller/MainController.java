package com.github.khovap.coursework.bookingsource_main.controller;



import com.github.khovap.coursework.bookingsource_main.model.Appointment;
import com.github.khovap.coursework.bookingsource_main.model.Client;
import com.github.khovap.coursework.bookingsource_main.model.Specialist;
import com.github.khovap.coursework.bookingsource_main.service.AppointmentEntityService;
import com.github.khovap.coursework.bookingsource_main.service.ClientEntityService;
import com.github.khovap.coursework.bookingsource_main.service.SpecialistEntityService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final ClientEntityService clientService;

    private final AppointmentEntityService appointmentEntityService;
    private final SpecialistEntityService specialistEntityService;

    @GetMapping("/")
    public String mainPage(Model model){
        return "index";
    }

    @GetMapping("/clients")
    @SneakyThrows
    public String getAllClients(Model model){
        List<Client> allClients = clientService.getAllClients();
        model.addAttribute("clients", allClients);
        return "clients" ;
    }

    @GetMapping("/profile/")
    public String showClientProfile(Model model){
        List<Appointment> appointments = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userPhoneNumber = auth.getName();
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))){
            Client client = clientService.getClientByPhoneNumber(userPhoneNumber);
            appointments = appointmentEntityService.getAppointmentByClient(client.getId());
            model.addAttribute("user", client);
        }
        if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_SPEC"))){
            Specialist specialist = specialistEntityService.getSpecialistByPhoneNumber(userPhoneNumber);
            appointments = appointmentEntityService.getOccupiedAppointmentBySpecialist(specialist.getId());
            model.addAttribute("user", specialist);
        }
        model.addAttribute("appointments", appointments);
        return "profile" ;
    }

    @PostMapping("/profile/cancelAppointment")
    @SneakyThrows
    public String cancelAppointment(@RequestParam("appointmentId") Long appointmentId){
        appointmentEntityService.updateAppointmentCancel(appointmentId);
        return "redirect:/profile/" ;
    }

}
