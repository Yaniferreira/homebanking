package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.dto.NewClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RoleType;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;
    @GetMapping("/clients")
    public List<ClientDTO>getClient(){
        return clientService.getAllClientsDTO();
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable  Long id){
       return clientService.getClientById(id);}
    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    private AccountController accountController;

    @PostMapping("/clients")
    public ResponseEntity<String> createClient(
       @RequestBody NewClientDTO newClientDto)
    {
        if(newClientDto.getFirstName().isBlank()){
            return new ResponseEntity<>("Name can't be blank", HttpStatus.FORBIDDEN);
        }
        if(newClientDto.getLastName().isBlank()){
            return new ResponseEntity<>("Last name can't be blank", HttpStatus.FORBIDDEN);
        }
        if(newClientDto.getEmail().isBlank()){
            return new ResponseEntity<>("Email can't be blank", HttpStatus.FORBIDDEN);
        }
        if(newClientDto.getPassword().isBlank()){
            return new ResponseEntity<>("Password can't be blank", HttpStatus.FORBIDDEN);
        }

        if(clientService.existsByEmail(newClientDto.getEmail())){
            return new ResponseEntity<>("Email already on use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(newClientDto.getFirstName(), newClientDto.getLastName(), newClientDto.getEmail(),
                passwordEncoder.encode(newClientDto.getPassword()), RoleType.CLIENT);
        clientService.saveClient(client);
        String number;
        do {
            number =Utils.generate();
        }while (accountService.existsByNumber(number));
        Account accountFirs= new Account(number, LocalDate.now(),0,0);
        client.addAccount(accountFirs);
        accountService.saveAccount(accountFirs);
        return new ResponseEntity<>("Client and account created", HttpStatus.CREATED);
    }
    @GetMapping("/clients/current")
    public ResponseEntity<Object> getOneClient (Authentication authentication){
        ClientDTO clientDTO = clientService.getAuthClientDTO(authentication.getName());
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }
}

