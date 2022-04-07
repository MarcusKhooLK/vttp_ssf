package sg.edu.nus.iss.workshop17.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.workshop17.model.MyCurrency;

@Service
public class CurrencyService {

    @Value("${free.curr.conv}")
    private String apiKey;

    private List<MyCurrency> currencies;

    @PostConstruct
    private void init() {
        Boolean hasKey = apiKey != null;
        System.out.println(">>> API Key set: " + hasKey);
    }


    public void getCurrencies(Model model) {
        String url = "https://free.currconv.com/api/v7/countries";
        url = UriComponentsBuilder
            .fromUriString(url)
            .queryParam("apiKey", apiKey)
            .toUriString();
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);

        System.out.println(">>> Response: " + resp.getStatusCode());

        InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject o = reader.readObject().getJsonObject("results");
        Set<String> keySet = o.keySet();

        currencies = new ArrayList<>();
        for(String key: keySet) {
            JsonObject country = o.getJsonObject(key);
            MyCurrency c = MyCurrency.create(country.toString());
            currencies.add(c);
        }

        model.addAttribute("allCurrencies", currencies);
    }

    public Double getConversion(String from, String to) {
        // GET /api/v7/convert?q=<from >_<to>&compact=ultra&apiKey=<api key>
        String url = "https://free.currconv.com/api/v7/convert";
        url = UriComponentsBuilder
            .fromUriString(url)
            .queryParam("compact", "ultra")
            .queryParam("q", "%s_%s".formatted(from, to))
            .queryParam("apiKey", apiKey)
            .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.getForEntity(url, String.class);

        System.out.println(">>> Response: " + resp.getStatusCode());

        InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
        JsonReader reader = Json.createReader(is);
        Double conversionRate = reader.readObject()
                                    .getJsonNumber("%s_%s".formatted(from, to))
                                    .doubleValue();

        return conversionRate;
    }

    public Optional<MyCurrency> findCurrencyById(String id) {
        for(MyCurrency c : currencies) {
            if(c.getId().compareTo(id) == 0) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }
}
