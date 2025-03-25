package com.example.sample.common.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageLevel {

    INFO("I"),
    WARNING("W"),
    ERROR("E");

    private final String value;
}
