package com.example.sample.domain.account.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.sample.domain.account.dto.Account;
import com.example.sample.domain.account.service.AccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    /**
     * 一覧表示
     * @param model
     * @return
     */
    @GetMapping("/account")
    public ModelAndView showList(ModelAndView model) {

        List<Account> accountList = service.findAll();

        model.setViewName("account/AccountList");
        model.addObject("accounts", accountList);
        return model;
    }
}
