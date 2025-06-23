package com.github.yildizmy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.yildizmy.dto.mapper.WeatherInfo;

@Service
@Slf4j
public class WeatherService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    public WeatherService(
        RestTemplateBuilder builder,
        @Value("${app.weather.api.base-url}") String baseUrl,
        @Value("${app.weather.api.key}") String apiKey
    ) {
        this.restTemplate = builder.build();
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public WeatherInfo getWeather(String city) {
        log.info("baseUrl: {}", baseUrl);
        ResponseEntity<WeatherInfo> response = restTemplate.getForEntity(baseUrl, WeatherInfo.class);
        return response.getBody();
    }
}
