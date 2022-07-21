package com.github.khovap.coursework.bookingsource_main.controller;

import org.apache.kafka.common.network.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ErrorsController {

    @GetMapping("/errorOccupiedApp")
    public String showError(){
        return "/errorOccupiedApp";
    }

}
