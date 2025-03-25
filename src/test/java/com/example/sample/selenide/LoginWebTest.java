package com.example.sample.selenide;

import static com.codeborne.selenide.Selenide.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.codeborne.selenide.Selenide;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginWebTest {

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
    public void ログイン成功_管理者() {
        open(url("/"));
        screenshot("/login/1/BeforeLogin");

        $("#username").setValue("admin");
        $("#password").setValue("password");
        $("#submit").click();

        screenshot("/login/1/WithAdminAfterLogin");
        
    }

    @Test
    public void ログイン成功_一般ユーザー() {
        open(url("/"));
        screenshot("/login/2/BeforeLogin");

        $("#username").setValue("user");
        $("#password").setValue("password");
        $("#submit").click();

        screenshot("/login/2/WithUserAfterLogin");
        
    }

    @Test
    public void ログイン失敗() {
        open(url("/"));

        $("#username").setValue("admin");
        $("#password").setValue("dummy");
        $("#submit").click();

        screenshot("/login/3/loginFailure");
        
    }
}
