package com.example.sample.domain.todo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.sample.common.exception.RecordNotFoundException;
import com.example.sample.domain.todo.dto.Todo;
import com.example.sample.domain.todo.service.TodoService;

import jakarta.servlet.ServletException;

@SpringBootTest
@ActiveProfiles("test")
public class TodoControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TodoController controller;
    
    @Mock
    private TodoService service;

    @Mock
    private MessageSource messageSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(controller);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void 登録_バリデーションエラーあり() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/todo/create"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("todo/TodoCreate"));

        verify(service, times(0)).insert(any());

    }

    @Test
    void 登録_バリデーションエラーなし() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/todo/create")
            .param("title", "title")
            .param("description", "description")
            .param("startDate", "2024-01-01")
            .param("deadline", "2025-01-01"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/todo"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("notification"));

        ArgumentCaptor<Todo> todoCaptor = ArgumentCaptor.forClass(Todo.class);
        verify(service, times(1)).insert(todoCaptor.capture());

        Todo actualArgument = todoCaptor.getValue();
        assertEquals("title", actualArgument.getTitle());
        assertEquals("description", actualArgument.getDescription());
        assertEquals(LocalDate.of(2024, 1, 1), actualArgument.getStartDate());
        assertEquals(LocalDate.of(2025, 1, 1), actualArgument.getDeadline());
        assertEquals(0, actualArgument.getProgress());
    }

    @Test
    void 登録画面遷移() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todo/createForm"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("todo/TodoCreate"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("createForm"));

    }

    @Test
    void 削除() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/todo/delete")
            .param("id","1"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/todo"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("notification"));

        verify(service, times(1)).delete(anyInt());
    }

    @Test
    void 編集画面遷移_レコードあり() throws Exception {
        Todo todo = new Todo();
        Mockito.when(service.findOne(anyInt())).thenReturn(Optional.ofNullable(todo));

        mockMvc.perform(MockMvcRequestBuilders.get("/todo/edit/{id}",1))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("todo/TodoEdit"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("updateForm"));
    }

    @Test
    void 編集画面遷移_レコードなし() throws Exception {
        Mockito.when(service.findOne(anyInt())).thenReturn(Optional.ofNullable(null));

        Throwable exception = assertThrows(ServletException.class,
            ()-> mockMvc.perform(MockMvcRequestBuilders.get("/todo/edit/{id}",1)))
            .getRootCause();

        assertEquals(exception.getClass(), RecordNotFoundException.class);
    }

    @Test
    void インデックス() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/todo"));

    }

    @Test
    void キャンセル() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todo/cancel"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/todo"));

    }

    @Test
    void 一覧表示() throws Exception {
        List<Todo> todoList = new ArrayList<>();

        Mockito.when(service.findAll()).thenReturn(todoList);
        mockMvc.perform(MockMvcRequestBuilders.get("/todo"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("todo/TodoList"))
            .andExpect(MockMvcResultMatchers.model().attribute("todos", todoList));

    }

    @Test
    void 更新_バリデーションエラーあり() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/todo/update"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("todo/TodoEdit"));

        verify(service, times(0)).update(any());
    }

    @Test
    void 更新_バリデーションエラーなし() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/todo/update")
            .param("title", "title")
            .param("description", "description")
            .param("startDate", "2024-01-01")
            .param("deadline", "2025-01-01")
            .param("progress", "10"))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.redirectedUrl("/todo"))
            .andExpect(MockMvcResultMatchers.flash().attributeExists("notification"));

        ArgumentCaptor<Todo> todoCaptor = ArgumentCaptor.forClass(Todo.class);
        verify(service, times(1)).update(todoCaptor.capture());

        Todo actualArgument = todoCaptor.getValue();
        assertEquals("title", actualArgument.getTitle());
        assertEquals("description", actualArgument.getDescription());
        assertEquals(LocalDate.of(2024, 1, 1), actualArgument.getStartDate());
        assertEquals(LocalDate.of(2025, 1, 1), actualArgument.getDeadline());
        assertEquals(10, actualArgument.getProgress());
    }
}
