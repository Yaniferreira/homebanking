package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List <Client> getAllClients();

    List<ClientDTO> getAllClientsDTO();

    Client getAuthClient(String email);

    ClientDTO getAuthClientDTO (String email);

    ClientDTO getClientById(Long id);

    boolean existsByEmail(String email);

    void saveClient(Client client);
    Client findByEmail(String email);
}
