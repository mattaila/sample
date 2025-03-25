package com.example.sample.domain.account.service;

import java.util.List;

import com.example.sample.domain.account.dto.Account;

public interface AccountService {

    List<Account> findAll();
}
