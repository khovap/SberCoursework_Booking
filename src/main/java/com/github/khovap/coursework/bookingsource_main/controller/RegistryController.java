package com.github.khovap.coursework.bookingsource_main.controller;

import com.github.khovap.coursework.bookingsource_main.model.Appointment;
import com.github.khovap.coursework.bookingsource_main.service.AppointmentEntityService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/registry")
@RequiredArgsConstructor
public class RegistryController {
    private final AppointmentEntityService appointmentService;

    @GetMapping("/allAppointments")
    @SneakyThrows
    public String getAllAppointments(Model model){
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments", allAppointments);
        return "registry/allAppointments" ;
    }

    @PostMapping("/createAppointmentTableNext2Weeks")
    @SneakyThrows
    public String createAppointments(){
        appointmentService.createAppointmentsTimetableNextMonth();
        System.out.println("Created");
                return "redirect:/registry/allAppointments";
    }


}
