package com.github.khovap.coursework.bookingsource_main.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Client {

    private long id;
    @NotBlank(message = "Поле не должно быть пустым")
    private String name;
    @NotBlank(message = "Поле не должно быть пустым")
    private String surname;
    private String patronymic;

    @NotNull
    @Size(min = 10, max = 10)
    @Pattern(regexp = "^\\d{10}$", message = "Телефонный номер должен содержать " +
            "10 цифр без разделительных знаков и символов")
    private String phoneNumber;
    private String healthInsurancePolicyNumber;
    private List<Appointment> appointments;

    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic;
    }
}
