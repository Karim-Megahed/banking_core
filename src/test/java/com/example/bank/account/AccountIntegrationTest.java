package com.example.bank.account;

import com.example.bank.balance.BalanceCurrency;
import com.example.bank.customer.Customer;
import com.example.bank.customer.CustomerRequest;
import com.example.bank.customer.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountIntegrationTest {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void itShouldCreateAndRetrieveAccountSuccessfully() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest("karim", "megahed", FakeEmail());
        Customer customer = customerService.createCustomer(customerRequest);
        List<BalanceCurrency> currencies = Arrays.asList(BalanceCurrency.EUR, BalanceCurrency.GBP);

        AccountRequest accountRequest = new AccountRequest(customer.getId(), "DE", currencies);
        MvcResult accountCreationResult = mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(ObjectToJson(accountRequest))))
                .andExpect(status().isCreated())
                .andReturn();
        AccountResponse accountCreationResponse = JsonToObject(accountCreationResult);

        MvcResult accountFetchingResult = mockMvc.perform(get("/api/v1/accounts/" + accountCreationResponse.getAccountId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        AccountResponse accountFetchingResponse = JsonToObject(accountFetchingResult);

        assertThat(accountFetchingResponse).usingRecursiveComparison().isEqualTo(accountCreationResponse);
        assertThat(accountFetchingResponse.getBalances().size()).isEqualTo(currencies.size());
    }

    private String FakeEmail(){
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService()
        );

        return fakeValuesService.bothify("????##@gmail.com");
    }

    private AccountResponse JsonToObject(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AccountResponse.class);
    }

    private String ObjectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
