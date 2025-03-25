package com.example.sample.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.sample.domain.todo.dto.Todo;

/**
 * Todoリポジトリクラス
 */
@Mapper
public interface TodoRepository {

    List<Todo> selectAll();
    Todo selectOne(int id);
    long countAll();
    void insert(Todo todo);
    int update(Todo todo);
    int delete(int id);
}
