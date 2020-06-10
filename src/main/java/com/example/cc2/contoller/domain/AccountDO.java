package com.example.cc2.contoller.domain;

import com.example.cc2.service.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.mail.internet.AddressException;
import java.util.Date;
import java.util.UUID;

public class AccountDO {

    @JsonProperty("uuid")
    private UUID uuid;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("emailAddress")
    private String emailAddress;
    @JsonProperty("dob")
    private Date dob;


    public AccountDO(UUID uuid, String firstName, String lastName, String emailAddress, Date dob) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dob = dob;
    }

    public AccountDO(){};

    public Account toAccount() throws AddressException {

        return new Account.Builder()
                .withUUID(uuid)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmailAddress(emailAddress)
                .withDOB(dob)
                .build();
    }

    public static AccountDO fromAccount(Account account) {

        return new AccountDO(account.getUuid(), account.getFirstName(), account.getLastName(), account.getEmailAddress(), account.getDob());
    }
}
