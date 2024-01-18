package com.mindhub.homebanking.services.impl;

import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientsRepository;

    @Override
    public List<ClientDTO> getAllClientsDTO() {
        return getAllClients().stream().map(ClientDTO::new).collect(Collectors.toList());
    }
    @Override
    public Client getAuthClient(String email) {
        return clientsRepository.findByEmail(email);
    }

    @Override
    public ClientDTO getAuthClientDTO(String email) {
        return new ClientDTO(getAuthClient(email));
    }

    @Override
    public ClientDTO getClientById(Long id) {
        return clientsRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public List <Client> getAllClients(){
        return clientsRepository.findAll();
    }

    @Override
    public boolean existsByEmail(String email){
        return clientsRepository.existsByEmail(email);
    }

    @Override
    public void saveClient(Client client){
        clientsRepository.save(client);
    }

    @Override
    public Client findByEmail(String email) {
        return clientsRepository.findByEmail(email);
    }
}
