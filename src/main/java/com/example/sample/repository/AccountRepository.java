package com.example.sample.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.sample.domain.account.dto.Account;

@Mapper
public interface AccountRepository {

    List<Account> selectAll();
    Account selectOne(String userName);
}
