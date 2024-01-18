package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.LoanService;
import com.mindhub.homebanking.dto.LoanApplicationDTO;
import com.mindhub.homebanking.dto.LoanDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/loans")
    public ResponseEntity<List<LoanDTO>> getLoans() {
        List<LoanDTO> availableLoans = loanService.getLoansDTO();
        return ResponseEntity.ok(availableLoans);
    }

    @PostMapping("/loans")
    @Transactional
    public ResponseEntity<Object> applyForLoan(
            @RequestBody LoanApplicationDTO loanApplicationDTO,
            Authentication authentication) {

        ResponseEntity<String> response = loanService.applyForLoan(loanApplicationDTO, authentication.getName());

        Account account = accountService.findByNumber(loanApplicationDTO.getAccountNumber());

        if (account == null) {
            return ResponseEntity.badRequest().body("Account not found");
        }

        Client client = account.getClient();

        Client authClient = clientService.getAuthClient(authentication.getName());
        if (authClient == null || !authClient.equals(client)) {
            return ResponseEntity.badRequest().body("Account does not belong to the authenticated Client.");
        }

        if (response.getStatusCode().is2xxSuccessful()) {

            LoanDTO loanDTO = loanService.findById(loanApplicationDTO.getLoanId());

            Transaction creditTransaction = new Transaction(account, LocalDateTime.now(),
                    loanApplicationDTO.getAmount(), TransactionType.CREDIT,
                    "Loan approved for " + loanDTO.getName());
            account.addTransaccion(creditTransaction);
            transactionRepository.save(creditTransaction);
            account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
            accountService.saveAccount(account);
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.badRequest().body(response.getBody());
        }
    }
}