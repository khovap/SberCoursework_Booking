package com.github.khovap.coursework.bookingsource_main.controller;

import com.github.khovap.coursework.bookingsource_main.entity.UserEntity;
import com.github.khovap.coursework.bookingsource_main.model.Client;
import com.github.khovap.coursework.bookingsource_main.service.implementation.DefaultUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final DefaultUserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserEntity());
        model.addAttribute("client", new Client());
        return "/registration";
    }

    @PostMapping("/registration/addUser")
    public String addUser(@Valid @ModelAttribute("userForm") UserEntity userForm,
                          BindingResult bindingResultUser,
                          @Valid @ModelAttribute ("client") Client client,
                          BindingResult bindingResultClient,
                          Model model
                          ) {
        if (bindingResultUser.hasErrors() || bindingResultClient.hasErrors()) {
            return "registration";
        }
        else {
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "/registration";
        }
        }
        client.setPhoneNumber(userForm.getPhoneNumber());
        if (!userService.saveUser(userForm, client)) {
            model.addAttribute("usernameError", "Пользователь с таким номером телефона уже существует");
            return "/registration";
        }
        return "redirect:/";
    }


}
