package com.example.sample.domain.todo.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.sample.domain.todo.dto.Todo;
import com.example.sample.repository.TodoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Todoサービス実装クラス
 */
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repository;

    /**
     * 一覧取得
     */
    @Override
    public List<Todo> findAll() {
        List<Todo> result = Collections.emptyList();
        long count = repository.countAll();

        if(0 < count) {
            result = repository.selectAll();
        }
        return result;
    }

    /**
     * レコード取得
     */
    @Override
    public Optional<Todo> findOne(int id) {
        return Optional.ofNullable(repository.selectOne(id));
    }

    /**
     * 登録
     */
    @Override
    @Transactional
    public void insert(Todo todo) {
        repository.insert(todo);
    }

    /**
     * 更新
     */
    @Override
    @Transactional
    public int update(Todo todo) {
        return repository.update(todo);
    }

    /**
     * 削除
     */
    @Override
    @Transactional
    public int delete(int id) {
        return repository.delete(id);
    }

}
