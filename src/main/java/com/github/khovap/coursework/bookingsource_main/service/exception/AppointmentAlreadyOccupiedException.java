package com.github.khovap.coursework.bookingsource_main.service.exception;

public class AppointmentAlreadyOccupiedException extends RuntimeException {
    public AppointmentAlreadyOccupiedException(String message) {
        super(message);
    }

}
