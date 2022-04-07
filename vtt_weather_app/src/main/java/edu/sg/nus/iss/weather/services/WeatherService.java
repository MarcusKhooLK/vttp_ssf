package edu.sg.nus.iss.weather.services;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import edu.sg.nus.iss.weather.model.Weather;

@Service
public class WeatherService {
    
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static String OPEN_WEATHER_APP_ID;
    
    @PostConstruct
    public void init() {
        OPEN_WEATHER_APP_ID = System.getenv("OPEN_WEATHER_APP_ID");
    }

    public Optional<Weather> getCurrentWeather(String city) {
        String url = UriComponentsBuilder
        .fromUriString(WEATHER_URL)
        .queryParam("q", city)
        .queryParam("appid", OPEN_WEATHER_APP_ID)
        .queryParam("units", "metric")
        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        
        try {
            ResponseEntity<String> resp = template.exchange(req, String.class);
            Weather w = Weather.create(resp.getBody());
            return Optional.of(w);
        }catch(Exception e){
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }

}
