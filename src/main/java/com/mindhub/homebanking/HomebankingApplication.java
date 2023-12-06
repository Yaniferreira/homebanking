package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.mindhub.homebanking.repositories.ClientsRepositories;

@SpringBootApplication
public class HomebankingApplication {
public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
public CommandLineRunner initData(ClientsRepositories clientsRepositories){
		return args -> {
			Client clientOne=new Client("Melba","Morel","melba@mindhub.com");
			System.out.println(clientOne);
			clientsRepositories.save(clientOne);
			System.out.println(clientOne);
		};

}
}
