package com.example.sample.common.object;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 画面表示通知
 */
@Data
@AllArgsConstructor
public class Notification {

    /** レベル */
    private String level;

    /** メッセージ */
    private String message;
}
