package com.github.yildizmy.service;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.yildizmy.dto.mapper.WeatherInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureWireMock(port = 9561)
@TestPropertySource(properties = {
        "app.weather.api.base-url=http://localhost:9561/data/2.5/weather"
})
public class WeatherServiceIT {

    @Autowired
    private WeatherService weatherService;

    @BeforeEach
    void setupMock() {
        WireMock.stubFor(WireMock.get(WireMock.urlMatching("/data/2.5/weather.*"))
            .willReturn(WireMock.aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {
                      "main": {
                        "temp": 295.15
                      }
                    }
                    """)));
    }

    @Test
    void testGetWeather() {
        WeatherInfo weather = weatherService.getWeather("Casablanca");
        assertNotNull(weather);
        assertEquals(295.15, weather.getMain().getTemp());
    }
}
