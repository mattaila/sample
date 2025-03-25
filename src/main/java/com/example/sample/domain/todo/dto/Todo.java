package com.example.sample.domain.todo.dto;

import java.time.LocalDate;

import lombok.Data;

/**
 * Todoオブジェクト
 */
@Data
public class Todo {

    /** ID */
    private long id;

    /** タイトル */
    private String title;

    /** 説明 */
    private String description;

    /** 開始日 */
    private LocalDate startDate;

    /** 締め切り */
    private LocalDate deadline;

    /** 進捗 */
    private int progress;
}
