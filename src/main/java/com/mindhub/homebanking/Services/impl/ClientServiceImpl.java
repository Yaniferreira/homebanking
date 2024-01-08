package com.mindhub.homebanking.Services.impl;

import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dto.ClienDTO;
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
    public List<ClienDTO> getAllClientsDTO() {
        return getAllClients().stream().map(ClienDTO::new).collect(Collectors.toList());
    }
    @Override
    public Client getAuthClient(String email) {
        return clientsRepositories.findByEmail(email);
    }

    @Override
    public ClienDTO getAuthClientDTO(String email) {
        return new ClienDTO(getAuthClient(email));
    }

    @Override
    public ClienDTO getClientById(Long id) {
        return clientsRepositories.findById(id).map(ClienDTO::new).orElse(null);
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
