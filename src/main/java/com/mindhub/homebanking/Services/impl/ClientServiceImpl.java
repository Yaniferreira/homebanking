package com.mindhub.homebanking.Services.impl;

import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientsRepositories clientsRepositories;

    @Override
    public List<ClientDTO> getAllClientsDTO() {
        return getAllClients().stream().map(ClientDTO::new).collect(Collectors.toList());
    }
    @Override
    public Client getAuthClient(String email) {
        return clientsRepositories.findByEmail(email);
    }

    @Override
    public ClientDTO getAuthClientDTO(String email) {
        return new ClientDTO(getAuthClient(email));
    }

    @Override
    public ClientDTO getClientById(Long id) {
        return clientsRepositories.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public List <Client> getAllClients(){
        return clientsRepositories.findAll();
    }

    @Override
    public boolean existsByEmail(String email){
        return clientsRepositories.existsByEmail(email);
    }

    @Override
    public void saveClient(Client client){
        clientsRepositories.save(client);
    }
}
