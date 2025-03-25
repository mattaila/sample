package com.example.sample.selenide;

import static com.codeborne.selenide.Selenide.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoWebTest {

    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1920x1080";
    }

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
    public void Todo_登録() {
        login();

        $("#create").click();
        screenshot("/todo/1/BeforeInput");

        $("#title").setValue("テスト");
        $("#description").setValue("説明");
        $("#startDate").setValue("002024-01-01");
        $("#deadline").setValue("002025-01-01");

        screenshot("/todo/1/BeforeCreate");
        $("#submit").click();

        screenshot("/todo/1/AfterCreate");
        
    }

    @Test
    public void Todo_登録_バリデーションエラーあり() {
        login();

        $("#create").click();

        screenshot("/todo/2/BeforeCreate");
        $("#submit").click();

        screenshot("/todo/2/AfterCreate");
        
    }

    @Test
    public void Todo_編集() {
        login();

        $("#edit").click();
        screenshot("/todo/3/BeforeInput");

        $("#title").setValue("テストupdated");
        $("#description").setValue("説明updated");
        $("#startDate").setValue("002025-01-01");
        $("#deadline").setValue("002027-01-01");
        $("#progress").setValue("50");

        screenshot("/todo/3/BeforeUpdate");
        $("#update").click();

        screenshot("/todo/3/AfterUpdate");
        
    }
    
    @Test
    public void Todo_編集_バリデーションエラーあり() {
        login();

        $("#edit").click();

        $("#startDate").setValue("002030-01-01");
        $("#deadline").setValue("002027-01-01");

        screenshot("/todo/4/BeforeUpdate");
        $("#update").click();

        screenshot("/todo/4/AfterUpdate");
        
    }

    @Test
    public void Todo_削除() {
        login();

        $("#edit").click();
        screenshot("/todo/5/BeforeDelete");
        $("#delete").click();

        screenshot("/todo/5/AfterDelete");
        
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
