package com.example.sample.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.sample.domain.account.dto.Account;

@MybatisTest
@ActiveProfiles("test")
@Transactional
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository repository;

    private static final String ENCODED_PASSWORD = "$2a$08$h0qbchHHG86Yd6xb8VImjuuaIAnfls7.9RLwcxJpVYfnsEiHDXTZG";

    @Test
    void selectAll() {
        List<Account> accountList = repository.selectAll();
        assertEquals(2, accountList.size());
        
    }

    @Test
    void selectOne() {
        Account actual = repository.selectOne("admin");
        assertAll(
            () -> assertEquals("admin", actual.getUsername()),
            () -> assertEquals("ADMIN", actual.getRole()),
            () -> assertEquals(LocalDate.of(2099, 1, 1), actual.getInvalidDate()),
            () -> assertEquals(ENCODED_PASSWORD, actual.getPassword())
        );
    }
}
