package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
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
								  AccountRepository accountRepository){
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
		};

}
}
