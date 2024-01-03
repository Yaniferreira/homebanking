package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String number;
  private LocalDate creationDate;
  private double balance;
  @ManyToOne
  private Client client;
  @OneToMany(mappedBy = "account",fetch = FetchType.EAGER)
  private Set<Transaction> transactions=new HashSet<>();

  public Account() {
  }

  public Account(String number, LocalDate creationDate, double balance) {
    this.number = number;
    this.creationDate = creationDate;
    this.balance = balance;
  }

  public Long getId() {
    return id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
  public Set<Transaction> getTransactions() {
    return transactions;
  }
  public void addTransaccion(Transaction transaction){
    transaction.setAccount(this);
    this.transactions.add(transaction);
  }

}
