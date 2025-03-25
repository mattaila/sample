package com.example.sample.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.example.sample.domain.todo.dto.Todo;

@MybatisTest
@ActiveProfiles("test")
@Transactional
public class TodoRepositoryTest {
    
    @Autowired
    private TodoRepository repository;

    @Test
    void countAll() {
        long count = repository.countAll();
        assertEquals(2, count);
    }

    @Test
    void selectAll() {
        List<Todo> todoList = repository.selectAll();
        assertEquals(2, todoList.size());
        
    }

    @Test
    void selectOne() {
        Todo todo = repository.selectOne(1);
        assertAll(
            () -> assertEquals(1, todo.getId()),
            () -> assertEquals("教育カリキュラム作成", todo.getTitle()),
            () -> assertEquals("研修用資料作成", todo.getDescription()),
            () -> assertEquals(LocalDate.of(2025, 1, 1), todo.getStartDate()),
            () -> assertEquals(LocalDate.of(2025, 3, 1), todo.getDeadline()),
            () -> assertEquals(30, todo.getProgress())
        );
        
    }

    @Test
    void insert() {
        Todo expected = new Todo();
        expected.setTitle("title");
        expected.setDescription("description");
        expected.setStartDate(LocalDate.of(2025, 1, 1));
        expected.setDeadline(LocalDate.of(2026, 1, 1));
        expected.setProgress(10);

        repository.insert(expected);

        Todo actual = repository.selectOne(3);
        
        assertAll(
            () -> assertEquals(expected.getTitle(), actual.getTitle()),
            () -> assertEquals(expected.getDescription(), actual.getDescription()),
            () -> assertEquals(expected.getStartDate(), actual.getStartDate()),
            () -> assertEquals(expected.getDeadline(), actual.getDeadline()),
            () -> assertEquals(expected.getProgress(), actual.getProgress())
        );
    }

    @Test
    void update() {
        Todo expected = new Todo();
        expected.setId(2);
        expected.setTitle("title");
        expected.setDescription("description");
        expected.setStartDate(LocalDate.of(2025, 1, 1));
        expected.setDeadline(LocalDate.of(2026, 1, 1));
        expected.setProgress(10);

        int updatedCount = repository.update(expected);
        Todo actual = repository.selectOne(2);

        assertAll(
            () -> assertEquals(1, updatedCount),
            () -> assertEquals(expected.getTitle(), actual.getTitle()),
            () -> assertEquals(expected.getDescription(), actual.getDescription()),
            () -> assertEquals(expected.getStartDate(), actual.getStartDate()),
            () -> assertEquals(expected.getDeadline(), actual.getDeadline()),
            () -> assertEquals(expected.getProgress(), actual.getProgress())
        );

    }

    @Test
    void delete() {
        int deletedCount = repository.delete(2);
        long count = repository.countAll();

        assertEquals(1, deletedCount);
        assertEquals(1, count);
    }
}
