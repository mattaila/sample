package com.example.sample.domain.account.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.sample.domain.account.dto.Account;
import com.example.sample.domain.account.service.AccountService;

@SpringBootTest
@ActiveProfiles("test")
public class AccountControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AccountController controller;

    @Mock
    private AccountService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(controller);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testShowList() throws UnsupportedEncodingException, Exception {

        List<Account> accountList = new ArrayList<>();
        
        Mockito.when(service.findAll()).thenReturn(accountList);
        mockMvc.perform(MockMvcRequestBuilders.get("/account"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("account/AccountList"))
            .andExpect(MockMvcResultMatchers.model().attribute("accounts", accountList));

    }
}
