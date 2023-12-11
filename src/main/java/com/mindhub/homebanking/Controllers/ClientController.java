package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.dto.ClienDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientsRepositories clientsRepositories;
    @RequestMapping("/clients")
    public List<ClienDTO>getClient(){
        return clientsRepositories.findAll()
                .stream()
                .map(client -> new ClienDTO(client))
                .collect(Collectors.toList());
    }
    @RequestMapping("/clients/{id}")
    public ClienDTO getClient(@PathVariable  Long id){
        return new ClienDTO (clientsRepositories.findById(id).orElse(null));
    }
}

