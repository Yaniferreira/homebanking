package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class HomebankingApplication {
public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Autowired
	public PasswordEncoder passwordEncoder;
	@Bean
public CommandLineRunner initData(ClientsRepositories clientsRepositories,
								  AccountRepository accountRepository,
								  TransactionRepository transactionRepository,
								  LoanRepository loanRepository,
								  ClientLoanRepository clientLoanRepository,
								  CardRepository cardRepository){
		return args -> {

			Client clientOne=new Client("Melba","Morel","melba@mindhub.com",
					passwordEncoder.encode("Melba001"));
			clientsRepositories.save(clientOne);
			System.out.println(clientOne);
			Account accountMel1=new Account("VIN001", LocalDate.now(),5000);
			Account accountMel2=new Account("VIN002",LocalDate.now().plusDays(1),7500);
			clientOne.addAccount(accountMel1);
			clientOne.addAccount(accountMel2);
			accountRepository.save(accountMel1);
			accountRepository.save(accountMel2);
			Client clientTwo=new Client("Yani","Ferreira","yaniferreira@gmail.com",
					passwordEncoder.encode("Yani002"));
			clientsRepositories.save(clientTwo);
			Account accountYani1=new Account("VIN003",LocalDate.now().plusDays(3),8000);
			Account accountYani2=new Account("VIN004",LocalDate.now().plusDays(5),3000);
			clientTwo.addAccount(accountYani1);
			clientTwo.addAccount(accountYani2);
			accountRepository.save(accountYani1);
			accountRepository.save(accountYani2);
			System.out.println(clientTwo);
			Transaction firstTransaction=new Transaction( -3000L,LocalDate.now(),"payment of expenses", TransactionType.DEBIT);
			Transaction secondTransaction=new Transaction(4000L,LocalDate.now(),"payment per sale",TransactionType.CREDIT);
			accountMel1.addTransaccion(firstTransaction);
			accountMel1.addTransaccion(secondTransaction);
			Transaction transaction1=new Transaction(12000L,LocalDate.now(),"salary payments",TransactionType.CREDIT);
			Transaction transaction2=new Transaction(-4500L,LocalDate.now(),"electric energy payment",TransactionType.DEBIT);
			accountMel2.addTransaccion(transaction1);
			accountMel2.addTransaccion(transaction2);
			transactionRepository.save(firstTransaction);
			transactionRepository.save(secondTransaction);
			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			Transaction transactionOtherClient=new Transaction(-1000L,LocalDate.now(),"payment of expenses",TransactionType.DEBIT);
			Transaction transactionOtherClient2=new Transaction(7000L,LocalDate.now(),"payment for shoe sales",TransactionType.CREDIT);
			accountYani1.addTransaccion(transactionOtherClient);
			accountYani2.addTransaccion(transactionOtherClient2);
			transactionRepository.save(transactionOtherClient);
			transactionRepository.save(transactionOtherClient2);
			Loan mortgage=new Loan("Mortgage",500.000, Set.of(12,24,36,48,60));
			Loan personal=new Loan("Personal",100.000,Set.of(6,12,24));
			Loan auto=new Loan("Automotriz",300.000,Set.of(6,12,24,36));
			loanRepository.save(mortgage);
			loanRepository.save(personal);
			loanRepository.save(auto);
			ClientLoan loanMelmortgage=new ClientLoan(400.000,60);
			ClientLoan loanMelPersonal=new ClientLoan(50.000,12);
			ClientLoan loanYaniPersonal=new ClientLoan(100.000,24);
			ClientLoan loanYaniAuto=new ClientLoan(200.000,36);
			clientOne.addClientLoan(loanMelmortgage);
			clientOne.addClientLoan(loanMelPersonal);
			clientTwo.addClientLoan(loanYaniAuto);
			clientTwo.addClientLoan(loanYaniPersonal);
			mortgage.ClientLoan(loanMelmortgage);
			personal.ClientLoan(loanMelPersonal);
			personal.ClientLoan(loanYaniPersonal);
			auto.ClientLoan(loanYaniAuto);
			clientLoanRepository.save(loanMelmortgage);
			clientLoanRepository.save(loanMelPersonal);
			clientLoanRepository.save(loanYaniPersonal);
			clientLoanRepository.save(loanYaniAuto);
			Card cardMel1= new Card(CardType.DEBIT,"4000 0012 3456 7899","001",
					LocalDate.now(),LocalDate.now().plusYears(5),"Melba Morel",CardColor.GOLD);
			Card cardMel2= new Card(CardType.CREDIT,"5412 3414 7892 6391","002",
					LocalDate.now(),LocalDate.now().plusYears(5),"Melba Morel",CardColor.TITANIUM);
			Card cardYani1=new Card(CardType.CREDIT,"4575 6568 5785 6787","003",
					LocalDate.now(),LocalDate.now().plusYears(5),"Yani Ferreira",CardColor.SILVER);
			clientOne.addCard(cardMel1);
			clientOne.addCard(cardMel2);
			clientTwo.addCard(cardYani1);
			cardRepository.save(cardMel1);
			cardRepository.save(cardMel2);
			cardRepository.save(cardYani1);
		};

}
}
