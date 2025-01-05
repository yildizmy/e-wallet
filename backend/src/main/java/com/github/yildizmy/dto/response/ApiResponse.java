package com.github.yildizmy.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Builds API response in a proper format with timestamp, message and data
 *
 * @param <T>
 */
@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final Long timestamp;
    private final String message;
    private final boolean success;
    private final T data;

    public ApiResponse(Long timestamp, String message, boolean success) {
        this.timestamp = timestamp;
        this.message = message;
        this.success = success;
        this.data = null;
    }
}
