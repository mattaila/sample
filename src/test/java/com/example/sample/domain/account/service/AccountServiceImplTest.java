package com.example.sample.domain.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.sample.domain.account.dto.Account;
import com.example.sample.repository.AccountRepository;

public class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl service;

    @Mock
    private AccountRepository repository;

    private AutoCloseable closable;
    private Account account;

    @BeforeEach
    void setUp() {
        closable = MockitoAnnotations.openMocks(this);
        account = new Account();
        account.setUsername("username");
    }

    @AfterEach
    void tearDown() throws Exception {
        closable.close();
    }

    @Test
    void findAll() {
        List<Account> expected = new ArrayList<>();
        expected.add(account);
        Mockito.when(repository.selectAll()).thenReturn(expected);

        List<Account> actual = service.findAll();
        verify(repository, times(1)).selectAll();

        assertEquals(1, actual.size());
        assertSame(expected, actual);
    }
}
