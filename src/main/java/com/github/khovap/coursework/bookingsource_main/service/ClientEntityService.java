package com.github.khovap.coursework.bookingsource_main.service;

import com.github.khovap.coursework.bookingsource_main.model.Client;

import java.util.List;

public interface ClientEntityService {

    Client getClientById(long id);
    List<Client> getAllClients();
    void addClient(Client client);
    List<Client> findClientByName(String name);

    Client getClientByPhoneNumber(String phoneNumber);
}
