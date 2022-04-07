package edu.sg.nus.iss.weather.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Weather {
    private static final String ICON_URL = "http://openweathermap.org/img/wn/%s";
    private String cityName;
    private String description;
    private String icon;
    private Double temp;
    private Double humidity;
    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Double getTemp() {
        return temp;
    }
    public void setTemp(Double temp) {
        this.temp = temp;
    }
    public Double getHumidity() {
        return humidity;
    }
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public static Weather create(String jsonString) throws IOException {
        Weather w = new Weather();

        try(InputStream is = new ByteArrayInputStream(jsonString.getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject root = reader.readObject();

            JsonArray weather = root.getJsonArray("weather");
            JsonObject main = root.getJsonObject("main");

            w.setCityName(root.getString("name"));
            w.setDescription(weather.getJsonObject(0).getString("main"));
            w.setIcon(ICON_URL.formatted(weather.getJsonObject(0).getString("icon") + ".png"));
            w.setHumidity(main.getJsonNumber("humidity").doubleValue());
            w.setTemp(main.getJsonNumber("temp").doubleValue());
        }

        return w;
    }
}
