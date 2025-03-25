package com.example.sample.domain.todo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.sample.common.validation.DateCheck;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 登録フォーム
 */
@Getter @Setter
@DateCheck
public class TodoCreateForm {

    /** タイトル */
    @NotBlank
    @Size(max = 20)
    private String title;

    /** 説明 */
    @Size(max = 30)
    private String description;

    /** 開始日 */
    @NotNull(message = "{message.common.validation.startDate}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
    private LocalDate startDate;

    /** 締め切り */
    @NotNull(message = "{message.common.validation.deadline}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Tokyo")
    private LocalDate deadline;

}
