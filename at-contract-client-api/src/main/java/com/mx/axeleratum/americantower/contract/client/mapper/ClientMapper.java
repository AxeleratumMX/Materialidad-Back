package com.mx.axeleratum.americantower.contract.client.mapper;

import com.mx.axeleratum.americantower.contract.client.dto.ClientDto;
import com.mx.axeleratum.americantower.contract.core.model.Client;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel="spring")
public interface ClientMapper {
    ClientDto toClientDto(Client client);

    Client toClient(ClientDto clientDto);

    List<ClientDto> toClientDtoList(Collection<Client> clients);

    List<Client> toClientList(Collection<ClientDto> clientsDtos);



}
