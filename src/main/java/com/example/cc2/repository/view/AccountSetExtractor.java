package com.example.cc2.repository.view;

import com.example.cc2.service.model.Account;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.mail.internet.AddressException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountSetExtractor implements ResultSetExtractor<List<Account>> {

    @Override
    public List<Account> extractData(ResultSet rs) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date parsedDate = dateFormat.parse(dob);
        while(rs.next()) {
            try {
                accounts.add(
                        new Account.Builder()
                                .withUUID(UUID.fromString(rs.getString("uuid")))
                                .withFirstName(rs.getString("first_name"))
                                .withLastName(rs.getString("last_name"))
                                .withEmailAddress(rs.getString("email"))
                                .withDOB(dateFormat.parse(rs.getString("dob")))
                                .build()
                );
            } catch (AddressException | ParseException e) {

                throw new SQLException();
            }
        }
        return accounts;
    }
}
