package com.example.sample.common.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.sample.domain.account.dto.Account;
import com.example.sample.repository.AccountRepository;

public class UserDetailServiceImplTest {

    @InjectMocks
    private UserDetailServiceImpl service;

    @Mock
    private AccountRepository repository;

    private AutoCloseable closable;

    @BeforeEach
    void setUp() {
        closable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closable.close();
    }

    @Test
    void ユーザーが見つからない() {
        Mockito.when(repository.selectOne(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
            () -> service.loadUserByUsername(anyString()));
    }

    @Test
    void 有効期限切れ() {
        Account account = new Account();
        account.setInvalidDate(LocalDate.of(1900, 1, 1));

        Mockito.when(repository.selectOne(anyString())).thenReturn(account);

        assertThrows(UsernameNotFoundException.class,
            () -> service.loadUserByUsername(anyString()));
    }

    @Test
    void 正常() {
        Account expected = new Account();
        expected.setUsername("admin");
        expected.setRole("ADMIN");
        expected.setEnabled(true);
        expected.setPassword("encoded");
        expected.setInvalidDate(LocalDate.of(2099, 1, 1));

        Mockito.when(repository.selectOne(anyString())).thenReturn(expected);

        UserDetails actual = service.loadUserByUsername(anyString());
        
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertTrue(actual.isEnabled());
    }
}
