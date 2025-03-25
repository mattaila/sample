package com.example.sample.domain.todo.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@SpringBootTest
@ActiveProfiles("test")
public class TodoUpdateFormTest {
    
    @Autowired
    private Validator validator;

    private TodoUpdateForm form;

    @BeforeEach
    void setUp() {
        LocalDate now = LocalDate.now();
        form = new TodoUpdateForm();
        form.setId(1);
        form.setTitle("title");
        form.setDescription("description");
        form.setStartDate(now);
        form.setDeadline(now);
        form.setProgress(10);
    }

    @Test
    void バリデーションエラーなし() {
        Errors errors = validator.validateObject(form);
        assertFalse(errors.hasErrors());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"aaaaaaaaaaaaaaaaaaaaa"}) //21文字
    void タイトルエラーあり(String title) {
        form.setTitle(title);
        Errors errors = validator.validateObject(form);
        assertTrue(errors.hasErrors());
        assertEquals("title", errors.getFieldErrors().get(0).getField());
    }

    @Test
    void 説明エラーあり() {
        form.setDescription("a".repeat(31)); //31文字
        Errors errors = validator.validateObject(form);
        assertTrue(errors.hasErrors());
        assertEquals("description", errors.getFieldErrors().get(0).getField());
    }

    @Test
    void 開始日エラーあり() {
        form.setStartDate(null);
        Errors errors = validator.validateObject(form);
        assertTrue(errors.hasErrors());
        assertEquals("startDate", errors.getFieldErrors().get(0).getField());
    }

    @Test
    void 締め切りエラーあり() {
        form.setDeadline(null);
        Errors errors = validator.validateObject(form);
        assertTrue(errors.hasErrors());
        assertEquals("deadline", errors.getFieldErrors().get(0).getField());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 101})
    void 進捗エラーあり(int progress) {
        form.setProgress(progress);
        Errors errors = validator.validateObject(form);
        assertTrue(errors.hasErrors());
        assertEquals("progress", errors.getFieldErrors().get(0).getField());
    }

    @Test
    void 日付相関チェックエラーあり() {
        form.setStartDate(LocalDate.of(2025, 1, 1));
        form.setDeadline(LocalDate.of(2024, 1, 1));

        Errors errors = validator.validateObject(form);
        assertTrue(errors.hasErrors());
        assertTrue(errors.hasFieldErrors("startDate"));
        assertTrue(errors.hasFieldErrors("deadline"));
    }
}
