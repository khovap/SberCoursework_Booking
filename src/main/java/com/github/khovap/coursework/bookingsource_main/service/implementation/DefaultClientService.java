package com.github.khovap.coursework.bookingsource_main.service.implementation;

import com.github.khovap.coursework.bookingsource_main.entity.ClientEntity;
import com.github.khovap.coursework.bookingsource_main.mapper.ClientToEntityMapper;
import com.github.khovap.coursework.bookingsource_main.model.Client;
import com.github.khovap.coursework.bookingsource_main.repository.ClientRepository;
import com.github.khovap.coursework.bookingsource_main.service.ClientEntityService;
import com.github.khovap.coursework.bookingsource_main.service.exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultClientService implements ClientEntityService {

    private final ClientRepository clientRepository;
    private final ClientToEntityMapper mapper;

    @Override
    @SneakyThrows
    public Client getClientById(long id) {
            ClientEntity client = clientRepository
                    .findById(id)
                    .orElseThrow(() -> new ClientNotFoundException("Client not found!"));

            return mapper.clientEntityToClient(client);
    }

    @Override
    public List<Client> getAllClients() {
        Iterable<ClientEntity> clientEntities = clientRepository.findAll();
        
        ArrayList<Client> clients = new ArrayList<>();
        for (ClientEntity a : clientEntities) {
            clients.add(mapper.clientEntityToClient(a));
        }
        return clients;
    }

    @Override
    public void addClient(Client client) {
        ClientEntity clientEntity =
                mapper.clientToClientEntity(client);
        clientRepository.save(clientEntity);
    }

    @Override
    public List<Client> findClientByName(String name) {
        Iterable<ClientEntity> clientEntities =
                clientRepository.findAllByNameContaining(name);

        ArrayList<Client> clients = new ArrayList<>();
        for (ClientEntity ce : clientEntities){
            clients.add(mapper.clientEntityToClient(ce));
        }
        return clients;
    }

    @Override
    public Client getClientByPhoneNumber(String phoneNumber){
        ClientEntity clientEntity = clientRepository.findByPhoneNumberEquals(phoneNumber);
        return  mapper.clientEntityToClient(clientEntity);
    }
}
