package com.example.sample.domain.todo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.sample.common.validation.DateCheck;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Todo更新用フォーム
 */
@Getter @Setter
@DateCheck
public class TodoUpdateForm {

    /** ID */
    private long id;

    /** タイトル */
    @NotBlank
    @Size(max = 20)
    private String title;

    /** 説明 */
    @Size(max = 30)
    private String description;

    /** 開始日 */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    /** 締め切り */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    /** 進捗 */
    @NotNull
    @Min(0) @Max(100)
    private int progress;

}
