package edu.sg.nus.iss.weather.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sg.nus.iss.weather.model.Weather;
import edu.sg.nus.iss.weather.services.WeatherService;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherSvc;
    
    @GetMapping(path="/search")
    public String getCityWeather(@RequestParam(name="city_name") String city, Model model) {
        
        Optional<Weather> weather = weatherSvc.getCurrentWeather(city);

        if(weather.isEmpty()) {
            return "error";
        }

        model.addAttribute("weather", weather.get());

        return "result";
    }

}
