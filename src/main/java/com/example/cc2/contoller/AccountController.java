package com.example.cc2.contoller;

import com.example.cc2.contoller.domain.AccountDO;
import com.example.cc2.contoller.domain.NewAccountDO;
import com.example.cc2.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.AddressException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccountDO>> getAccounts(){
        try {
            return ResponseEntity.ok(accountService.getAllUsers().stream().map(x -> AccountDO.fromAccount(x)).collect(Collectors.toList()));
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createAccount(@RequestBody NewAccountDO accountDO){

        try {
            accountService.createAccount(accountDO.toAccount());
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (AddressException | ParseException e) {
            logger.error("An exception occurred while persisting account", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity deleteAccount(@PathVariable UUID uuid){
        try {
            accountService.deleteAccount(uuid);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An exception occurred while deleting account", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editAccount(@RequestBody AccountDO accountDO){
        try {
            accountService.editAccount(accountDO.toAccount());
            return new ResponseEntity(HttpStatus.OK);
        } catch (AddressException e) {
            logger.error("An exception occurred while editing account", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

}
