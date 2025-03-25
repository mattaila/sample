package com.example.sample.common.service;

import java.util.Objects;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.sample.domain.account.dto.Account;
import com.example.sample.repository.AccountRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    private final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.selectOne(username);
		if(Objects.isNull(account)){
			log.debug("ユーザー:" + username + "が見つかりません。");
			throw new UsernameNotFoundException(username);
		}
		
		//有効期限が切れている場合は例外をスローする
		if(account.isExpired()) {
			log.debug("ユーザー:" + username + "は有効期限が切れています");
			throw new UsernameNotFoundException(username);
		}
		
		// 見つかったユーザー情報により認証情報を生成する
		return User
            .withUsername(account.getUsername())
            .password(account.getPassword())
            .roles(account.getRole())
            .disabled(!account.isEnabled())
            .build();
    }

}
