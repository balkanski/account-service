package com.example.cc2.service;

import com.example.cc2.repository.AccountRepository;
import com.example.cc2.service.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.internet.AddressException;
import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    void getAllUsers() {

        accountService.getAllUsers();
        verify(accountRepository, times(1)).getAllUsers();

    }

    @Test
    void createAccount() throws AddressException {

        Date dob = new Date();
        Account account = new Account.Builder()
                .withFirstName("John")
                .withLastName("Doe")
                .withEmailAddress("jd@gmail.com")
                .withDOB(dob)
                .build();

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        accountService.createAccount(account);

        verify(accountRepository, times(1)).createAccount(accountCaptor.capture());

        assertThat(accountCaptor.getValue().getFirstName()).isEqualTo("John");
        assertThat(accountCaptor.getValue().getLastName()).isEqualTo("Doe");
        assertThat(accountCaptor.getValue().getEmailAddress()).isEqualTo("jd@gmail.com");
        assertThat(accountCaptor.getValue().getDob()).isEqualTo(dob);

    }

    @Test
    void deleteAccount() {

        UUID uuid = UUID.randomUUID();

        accountService.deleteAccount(uuid);
        verify(accountRepository, times(1)).deleteAccount(uuid);
    }

    @Test
    void editAccount() throws AddressException {

        UUID uuid = UUID.randomUUID();
        Date dob = new Date();

        Account account = new Account.Builder()
                .withUUID(uuid)
                .withFirstName("John")
                .withLastName("Doe")
                .withEmailAddress("jd@gmail.com")
                .withDOB(dob)
                .build();

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);

        accountService.editAccount(account);

        verify(accountRepository, times(1)).editAccount(accountCaptor.capture());

        assertThat(accountCaptor.getValue().getUuid()).isEqualByComparingTo(uuid);
        assertThat(accountCaptor.getValue().getFirstName()).isEqualTo("John");
        assertThat(accountCaptor.getValue().getLastName()).isEqualTo("Doe");
        assertThat(accountCaptor.getValue().getEmailAddress()).isEqualTo("jd@gmail.com");
        assertThat(accountCaptor.getValue().getDob()).isEqualTo(dob);
    }
}
