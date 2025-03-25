package com.example.sample.domain.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.example.sample.domain.todo.dto.Todo;
import com.example.sample.repository.TodoRepository;

public class TodoServiceImplTest {

    @InjectMocks
    private TodoServiceImpl service;

    @Mock
    private TodoRepository repository;

    private AutoCloseable closable;
    private Todo todo;
    
    @BeforeEach
    void setUp() {
        closable = MockitoAnnotations.openMocks(this);
        todo = new Todo();
        todo.setId(1);
        todo.setTitle("title");
        todo.setDescription("description");
        todo.setDeadline(LocalDate.of(2025, 1, 1));
        todo.setStartDate(LocalDate.of(2025, 1, 2));
        todo.setProgress(0);
    }

    @AfterEach
    void tearDown() throws Exception {
        closable.close();
    }

    @Test
    void findAll_count0() {
        Mockito.when(repository.countAll()).thenReturn((long) 0);

        List<Todo> actual = service.findAll();
        verify(repository, times(1)).countAll();
        verify(repository, times(0)).selectAll();

        assertEquals(0, actual.size());
    }

    @Test
    void findAll_count1() {
        List<Todo> expected = new ArrayList<>();
        expected.add(todo);

        Mockito.when(repository.countAll()).thenReturn((long) 1);
        Mockito.when(repository.selectAll()).thenReturn(expected);

        List<Todo> actual = service.findAll();
        verify(repository, times(1)).countAll();
        verify(repository, times(1)).selectAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void findOne() {

        Mockito.when(repository.selectOne(anyInt())).thenReturn(todo);
        Optional<Todo> actual = service.findOne(anyInt());

        verify(repository, times(1)).selectOne(anyInt());
        assertTrue(actual.isPresent());
        assertEquals(todo, actual.get());
    }

    @Test
    void insert() {
        ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);
        
        Mockito.doNothing().when(repository).insert(any());
        service.insert(todo);

        verify(repository, times(1)).insert(captor.capture());
        assertSame(todo, captor.getValue());
    }

    @Test
    void update() {
        ArgumentCaptor<Todo> captor = ArgumentCaptor.forClass(Todo.class);
        
        Mockito.when(repository.update(any())).thenReturn(1);
        int actual = service.update(todo);

        verify(repository, times(1)).update(captor.capture());

        assertEquals(1, actual);
        assertSame(todo, captor.getValue());
    }

    @Test
    void delete() {
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        
        int id = 1;
        Mockito.when(repository.delete(anyInt())).thenReturn(1);
        int actual = service.delete(id);

        verify(repository, times(1)).delete(captor.capture());

        assertEquals(1, actual);
        assertSame(id, captor.getValue());
    }
}
