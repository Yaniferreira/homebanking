package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card,Long>{
    @Query("SELECT a From Account a "+
            "WHERE a.id = :id AND "+
            "a.client.id IN ( "+
            " SELECT c.id FROM Client c "+
            "WHERE c.email LIKE :email)")
    Account findByIdAndClientEmail(@Param("id")Long id,@Param("email")String email);
}
