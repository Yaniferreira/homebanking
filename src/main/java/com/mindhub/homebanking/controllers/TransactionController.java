package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import com.mindhub.homebanking.dto.NewTransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientService clientService;

    @PostMapping("/transfer")
    @Transactional
    public ResponseEntity<String> transferMoney(
            @RequestBody NewTransactionDTO newTransactionDTO,Authentication authentication){

        Account sourceAccount = accountService.findByNumber(newTransactionDTO.getSourceAccountNumber());
        Account targetAccount = accountService.findByNumber(newTransactionDTO.getTargetAccountNumber());

        if(newTransactionDTO.getAmount() == 0 || newTransactionDTO.getAmount() <= 0){
            return new ResponseEntity<>("Amount cannot be blank or cero", HttpStatus.FORBIDDEN);
        }

        if(newTransactionDTO.getDescriptions().isBlank()){
            return new ResponseEntity<>("Description cannot be blank",HttpStatus.FORBIDDEN);
        }


        if(newTransactionDTO.getSourceAccountNumber().isBlank() || newTransactionDTO.getTargetAccountNumber().isBlank()){
            return new ResponseEntity<>("Source Account or Target Account cannot be blank.",HttpStatus.FORBIDDEN);
        }

        if(sourceAccount == null){
            return new ResponseEntity<>("Source account does not exists.", HttpStatus.FORBIDDEN);
        }

        if(targetAccount == null){
            return new ResponseEntity<>("Target account does not exists.", HttpStatus.FORBIDDEN);
        }

        if(!sourceAccount.getClient().getEmail().equals(authentication.getName())){
            return new ResponseEntity<>("Source account does not belong to an authenticated client.", HttpStatus.FORBIDDEN);
        }

        if(sourceAccount.getBalance() < newTransactionDTO.getAmount()){
            return new ResponseEntity<>("Insufficent funds in the account",HttpStatus.BAD_REQUEST);
        }

        if(sourceAccount.equals(targetAccount)){
            return new ResponseEntity<>("Source and Target account cannot be the same", HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction(newTransactionDTO.getAmount(), LocalDateTime.now(),newTransactionDTO.getDescriptions(), TransactionType.DEBIT);
        Transaction creditTransaction = new Transaction(newTransactionDTO.getAmount(), LocalDateTime.now(),newTransactionDTO.getDescriptions(), TransactionType.CREDIT);
        debitTransaction.setAccount(sourceAccount);
        creditTransaction.setAccount(targetAccount);
        transactionService.saveTransaction(debitTransaction);
        transactionService.saveTransaction(creditTransaction);
        sourceAccount.setTotalBalance(sourceAccount.getBalance() - newTransactionDTO.getAmount());
        targetAccount.setTotalBalance(targetAccount.getBalance() + newTransactionDTO.getAmount());
        accountService.saveAccount(sourceAccount);
        accountService.saveAccount(targetAccount);

        return new ResponseEntity<>("Transfer succesful",HttpStatus.OK);
    }
}