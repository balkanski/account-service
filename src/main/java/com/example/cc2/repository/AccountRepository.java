package com.example.cc2.repository;

import com.example.cc2.repository.view.AccountSetExtractor;
import com.example.cc2.service.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AccountRepository {

    final private String getAllUsers = "SELECT uuid, first_name, last_name, email, to_char(dob, 'DD/MM/YYYY') as dob FROM accounts";

    final private String createUser =  "INSERT INTO accounts(uuid, first_name, last_name, email, dob) VALUES(?, ?, ?, ?, ?)";

    final private String deleteUser = "DELETE FROM accounts WHERE uuid=?";

    final private String editAccount = "UPDATE accounts SET first_name=?, last_name=?, email=?, dob=? WHERE uuid=?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Account> getAllUsers() {
        List<Account> accounts = jdbcTemplate.query(getAllUsers, new AccountSetExtractor());
        return accounts;
    }

    public void createAccount(Account account) {
        jdbcTemplate.update(createUser,
                account.getUuid(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmailAddress(),
                account.getDob());
    }

    public void deleteAccount(UUID uuid) {
        jdbcTemplate.update(deleteUser, uuid);
    }

    public void editAccount(Account account) {
        jdbcTemplate.update(editAccount,
                account.getFirstName(),
                account.getLastName(),
                account.getEmailAddress(),
                account.getDob(),
                account.getUuid()
        );
    }
}
