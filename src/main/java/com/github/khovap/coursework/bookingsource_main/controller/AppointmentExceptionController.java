package com.github.khovap.coursework.bookingsource_main.controller;

import com.github.khovap.coursework.bookingsource_main.service.exception.AppointmentAlreadyOccupiedException;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class AppointmentExceptionController {

    @ExceptionHandler(value = AppointmentAlreadyOccupiedException.class)
    public String appointmentException(AppointmentAlreadyOccupiedException e){
        return "redirect:/errorOccupiedApp/";
    }


}
