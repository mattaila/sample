package com.example.sample.domain.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HashController {

    @Autowired
	PasswordEncoder encoder;
	
	@GetMapping(value="/makehash", params="password")
	public String index(@RequestParam("password") String password) {
		// パスワードエンコーダーを使用してパラメータをハッシュ化する
        String encodedPassword = encoder.encode(password);

        // ハッシュ化したパスワードを出力する
        return encodedPassword;
	}
}
