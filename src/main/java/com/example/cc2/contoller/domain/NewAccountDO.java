package com.example.cc2.contoller.domain;

import com.example.cc2.service.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.mail.internet.AddressException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class NewAccountDO {

    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("emailAddress")
    private String emailAddress;
    @JsonProperty("dob")
    private String dob;

    public NewAccountDO(){};

    public Account toAccount() throws AddressException, ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = dateFormat.parse(dob);


        return new Account.Builder()
                .withUUID(UUID.randomUUID())
                .withFirstName(firstName)
                .withLastName(lastName)
                .withEmailAddress(emailAddress)
                .withDOB(parsedDate)
                .build();
    }


}

