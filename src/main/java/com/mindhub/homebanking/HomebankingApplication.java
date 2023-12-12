package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.mindhub.homebanking.repositories.ClientsRepositories;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {
public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
public CommandLineRunner initData(ClientsRepositories clientsRepositories,
								  AccountRepository accountRepository, TransactionRepository transactionRepository){
		return args -> {
			Client clientOne=new Client("Melba","Morel","melba@mindhub.com");
			clientsRepositories.save(clientOne);
			System.out.println(clientOne);
			Account accountMel1=new Account("VIN001", LocalDate.now(),5000);
			Account accountMel2=new Account("VIN002",LocalDate.now().plusDays(1),7500);
			clientOne.addAccount(accountMel1);
			clientOne.addAccount(accountMel2);
			accountRepository.save(accountMel1);
			accountRepository.save(accountMel2);
			Client clientTwo=new Client("Yani","Ferreira","yaniferreira@gmail.com");
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
		};

}
}
