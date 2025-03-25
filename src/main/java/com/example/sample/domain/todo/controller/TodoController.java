package com.example.sample.domain.todo.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.BeanUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sample.common.constants.MessageLevel;
import com.example.sample.common.exception.RecordNotFoundException;
import com.example.sample.common.object.Notification;
import com.example.sample.domain.todo.dto.Todo;
import com.example.sample.domain.todo.form.TodoCreateForm;
import com.example.sample.domain.todo.form.TodoUpdateForm;
import com.example.sample.domain.todo.service.TodoService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Todo関連Controller
 */
@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;
    private final MessageSource messageSource;

    @GetMapping({"/", "/todo/cancel"})
    public String showIndex() {
        return "redirect:/todo";
    }

    /**
     * 一覧表示
     * @param model
     * @return
     */
    @GetMapping("/todo")
    public ModelAndView showList(ModelAndView model) {

        List<Todo> todoList = service.findAll();

        model.setViewName("todo/TodoList");
        model.addObject("todos", todoList);
        return model;
    }

    /**
     * 新規作成画面へ遷移
     * @param model
     * @return
     */
    @GetMapping("/todo/createForm")
    public ModelAndView createForm(ModelAndView model) {

        model.addObject("createForm", new TodoCreateForm());
        model.setViewName("todo/TodoCreate");
        return model;
    }

    /**
     * 新規作成
     * @param model
     * @param createForm
     * @param bindingResult
     * @param redirectAttribute
     * @return
     */
    @PostMapping("/todo/create")
    public ModelAndView create(ModelAndView model, @Validated @ModelAttribute("createForm") TodoCreateForm createForm,
        BindingResult bindingResult, RedirectAttributes redirectAttribute) {

        if(bindingResult.hasErrors()) {
            model.setViewName("todo/TodoCreate");
            return model;
        }

        Todo todo = new Todo();
        BeanUtils.copyProperties(createForm, todo);
        todo.setProgress(0);

        service.insert(todo);

        model.setViewName("redirect:/todo");
        String msg = messageSource.getMessage("message.common.insert.success", null, Locale.getDefault());
        redirectAttribute.addFlashAttribute("notification", new Notification(MessageLevel.INFO.getValue(), msg));
        return model;
    }

    /**
     * 編集ページ遷移
     * @param model
     * @param id
     * @return
     * @throws RecordNotFoundException
     */
    @GetMapping("/todo/edit/{id}")
    public ModelAndView edit(ModelAndView model, @PathVariable("id") int id) 
        throws RecordNotFoundException {

        Todo todo = service.findOne(id).orElseThrow(() -> new RecordNotFoundException());
        TodoUpdateForm form = new TodoUpdateForm();
        BeanUtils.copyProperties(todo, form);

        model.setViewName("todo/TodoEdit");
        model.addObject("updateForm", form);
        return model;
    }

    /**
     * 更新
     * @param model
     * @param updateForm
     * @param bindingResult
     * @param redirectAttribute
     * @return
     */
    @PostMapping("/todo/update")
    public ModelAndView update(ModelAndView model, @Validated @ModelAttribute("updateForm") TodoUpdateForm updateForm,
        BindingResult bindingResult, RedirectAttributes redirectAttribute) {

        if(bindingResult.hasErrors()) {
            model.setViewName("todo/TodoEdit");
            return model;
        }

        Todo todo = new Todo();
        BeanUtils.copyProperties(updateForm, todo);
        service.update(todo);

        model.setViewName("redirect:/todo");

        String msg = messageSource.getMessage("message.common.update.success", null, Locale.getDefault());
        redirectAttribute.addFlashAttribute("notification", new Notification(MessageLevel.INFO.getValue(), msg));
        return model;
    }

    /**
     * 削除
     * @param model
     * @return
     */
    @PostMapping("/todo/delete")
    public ModelAndView delete(ModelAndView model, @RequestParam("id") int id, RedirectAttributes redirectAttribute) {

        service.delete(id);

        model.setViewName("redirect:/todo");
        String msg = messageSource.getMessage("message.common.delete.success", null, Locale.getDefault());
        redirectAttribute.addFlashAttribute("notification", new Notification(MessageLevel.INFO.getValue(), msg));
        return model;
    }
    
}
