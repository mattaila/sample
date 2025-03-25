package com.example.sample.domain.account.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Account {

    private String username;
	private String password;
	private String role;
	private boolean enabled;
	private LocalDate invalidDate;

	/**
	 * 有効期限切れかどうかをチェック
	 * @return
	 */
	public boolean isExpired() {
		return invalidDate.isBefore(LocalDate.now());
	}
    
}
