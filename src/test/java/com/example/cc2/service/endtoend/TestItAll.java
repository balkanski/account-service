package com.example.cc2.service.endtoend;

import com.example.cc2.CoreApplication;
import com.example.cc2.contoller.domain.AccountDO;
import com.example.cc2.service.model.Account;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CoreApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
public class TestItAll {

    @Autowired
    private MockMvc mockMvc;

    private String account1 = "{\n" +
            "\t\"firstName\":\"John\",\n" +
            "\t\"lastName\":\"Doe\",\n" +
            "\t\"emailAddress\":\"jd@gmail.com\",\n" +
            "\t\"dob\": \"11/11/1996\"\n" +
            "}";

    private String account2 = "{\n" +
            "\t\"firstName\":\"Jane\",\n" +
            "\t\"lastName\":\"Johnson\",\n" +
            "\t\"emailAddress\":\"jj@gmail.com\",\n" +
            "\t\"dob\": \"12/10/1996\"\n" +
            "}";

    private String edited = "{\n" +
            "\t\"firstName\":\"Johnny\",\n" +
            "\t\"lastName\":\"Do\",\n" +
            "\t\"emailAddress\":\"jt@gmail.com\",\n" +
            "\t\"dob\": \"11/11/1995\"\n" +
            "}";

    private String accountList = "[{\n" +
            "\t\"firstName\":\"John\",\n" +
            "\t\"lastName\":\"Doe\",\n" +
            "\t\"emailAddress\":\"jd@gmail.com\",\n" +
            "\t\"dob\": \"1996-11-11T00:00:00.000+0000\"\n" +
            "}]";


    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    String date = "12/10/1995";


    private String patchedAccountList = "[{\n" +
            "\t\"firstName\":\"Jane\",\n" +
            "\t\"lastName\":\"Dane\",\n" +
            "\t\"emailAddress\":\"jj@yahoo.com\",\n" +
            "\t\"dob\": \"1995-10-11T23:00:00.000+0000\"\n" +
            "}]";


    @Test
    public void testCreateGet() throws Exception {

        mockMvc.perform(post("/accounts").contentType("application/json").content(account1))
                .andDo(print()).andExpect(status().isCreated());


        MvcResult result = mockMvc.perform(get("/accounts").contentType("application/json"))
                .andDo(print()).andExpect(content().json(accountList))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<AccountDO> accountList = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<AccountDO>>(){});


        Date parsedDate = dateFormat.parse(date);
        Account patchedAccount = new Account.Builder()
                .withUUID(accountList.get(0).toAccount().getUuid())
                .withFirstName("Jane")
                .withLastName("Dane")
                .withEmailAddress("jj@yahoo.com")
                .withDOB(parsedDate)
                .build();


        mockMvc.perform(put("/accounts").contentType("application/json").content(objectMapper.writeValueAsString(AccountDO.fromAccount(patchedAccount))))
                .andDo(print()).andExpect(status().isOk());

        mockMvc.perform(get("/accounts").contentType("application/json"))
                .andDo(print()).andExpect(content().json(patchedAccountList));

        mockMvc.perform(delete(String.format("/accounts/%s", accountList.get(0).toAccount().getUuid())).contentType("application/json"))
                .andDo(print()).andExpect(status().isOk());

        mockMvc.perform(get("/accounts").contentType("application/json"))
                .andDo(print()).andExpect(content().json("[]"));
    }

}
