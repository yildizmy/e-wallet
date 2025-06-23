package com.github.yildizmy.dto.mapper;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class WeatherInfo {
    private Main main;

    @Getter
    public static class Main {
        private double temp;
        // getters and setters
    }

    // getters and setters
}
