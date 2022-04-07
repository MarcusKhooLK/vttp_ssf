package sg.edu.nus.iss.workshop17.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Currency;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class MyCurrency {
    private String id;
    private String currencyName;
    private String currencySymbol;
    private String countryName;
    
    public String getCountryName() {
        return countryName;
    }
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCurrencyName() {
        return currencyName;
    }
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
    public String getCurrencySymbol() {
        return currencySymbol;
    }
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public static MyCurrency create(String jsonString) {
        MyCurrency c = new MyCurrency();
        InputStream is = new ByteArrayInputStream(jsonString.getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject o = reader.readObject();

        c.id = o.getString("currencyId");
        Currency jC = Currency.getInstance(c.id);
        c.currencyName = o.getString("currencyName");
        c.currencySymbol = jC.getSymbol();
        c.countryName = o.getString("name");
        return c;
    }
}
