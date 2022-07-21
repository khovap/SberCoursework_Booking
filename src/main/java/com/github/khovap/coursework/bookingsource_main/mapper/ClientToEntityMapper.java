package com.github.khovap.coursework.bookingsource_main.mapper;

import com.github.khovap.coursework.bookingsource_main.entity.ClientEntity;
import com.github.khovap.coursework.bookingsource_main.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientToEntityMapper {
    ClientEntity clientToClientEntity(Client client);
    Client clientEntityToClient(ClientEntity clientEntity);
}
