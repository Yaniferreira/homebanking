package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.dto.ClienDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List <Client> getAllClients();

    List<ClienDTO> getAllClientsDTO();

    Client getAuthClient(String email);

    ClienDTO getAuthClientDTO (String email);

    ClienDTO getClientById(Long id);

    boolean existsByEmail(String email);

    void saveClient(Client client);
}
