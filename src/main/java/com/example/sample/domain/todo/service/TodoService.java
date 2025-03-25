package com.example.sample.domain.todo.service;

import java.util.List;
import java.util.Optional;

import com.example.sample.domain.todo.dto.Todo;

/**
 * Todoサービスinterface
 */
public interface TodoService {

    List<Todo> findAll();
    Optional<Todo> findOne(int id);
    void insert(Todo todo);
    int update(Todo todo);
    int delete(int id);
}
