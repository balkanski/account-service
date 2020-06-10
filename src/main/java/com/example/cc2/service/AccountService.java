package com.example.cc2.service;

import com.example.cc2.repository.AccountRepository;
import com.example.cc2.service.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllUsers(){
        return accountRepository.getAllUsers();
    }

    public void createAccount(Account account){
        accountRepository.createAccount(account);
    }

    public void deleteAccount(UUID uuid){
        accountRepository.deleteAccount(uuid);
    }

    public void editAccount(Account account){
        accountRepository.editAccount(account);
    }
}
