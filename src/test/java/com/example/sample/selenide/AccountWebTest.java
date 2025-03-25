package com.example.sample.selenide;

import static com.codeborne.selenide.Selenide.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.codeborne.selenide.Selenide;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountWebTest {

    @AfterEach
	void finishTestMethod() {
		Selenide.clearBrowserCookies();
	}

    @LocalServerPort
    private int port;
 
    private String url(String path) {
        return "http://localhost:%d%s".formatted(port, path);
    }

    @Test
    public void ユーザー一覧取得() {
        login();

        open(url("/account"));
        screenshot("/account/1/list");
        
    }

    /**
     * ログイン処理
     */
    private void login() {
        open(url("/"));

        $("#username").setValue("admin");
        $("#password").setValue("password");
        $("#submit").click();
    }
}
