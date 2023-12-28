package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.ClienDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RoleType;
import com.mindhub.homebanking.repositories.ClientsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    private AccountController accountController;

    @PostMapping("/clients")
    public ResponseEntity<String> createClient(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password)
    {
        if(firstName.isBlank()){
            return new ResponseEntity<>("Name can't be blank", HttpStatus.FORBIDDEN);
        }
        if(lastName.isBlank()){
            return new ResponseEntity<>("Last name can't be blank", HttpStatus.FORBIDDEN);
        }
        if(email.isBlank()){
            return new ResponseEntity<>("Email can't be blank", HttpStatus.FORBIDDEN);
        }
        if(password.isBlank()){
            return new ResponseEntity<>("Password can't be blank", HttpStatus.FORBIDDEN);
        }

        if(clientsRepositories.existsByEmail(email)){
            return new ResponseEntity<>("Email already on use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName,lastName,email,passwordEncoder.encode(password), RoleType.CLIENT);
        clientsRepositories.save(client);

        ResponseEntity<String> accountCreationResult = accountController.createAccountFirst(client);

        if (accountCreationResult.getStatusCode() != HttpStatus.CREATED) {
            return new ResponseEntity<>("Failed to create client account", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Client and account created", HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ResponseEntity<Object> getOneClient (Authentication authentication){

        Client client = clientsRepositories.findByEmail(authentication.getName());

        ClienDTO clientDTO = new ClienDTO(client);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }
}

