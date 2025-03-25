package com.example.sample.domain.account.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.sample.domain.account.dto.Account;
import com.example.sample.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Override
    public List<Account> findAll() {
        return repository.selectAll();
    }

}
