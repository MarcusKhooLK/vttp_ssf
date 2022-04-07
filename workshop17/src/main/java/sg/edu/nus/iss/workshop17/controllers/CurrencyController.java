package sg.edu.nus.iss.workshop17.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.workshop17.model.MyCurrency;
import sg.edu.nus.iss.workshop17.services.CurrencyService;

@Controller
@RequestMapping(path="", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyController {

    @Autowired
    private CurrencyService currencySvc;
    
    @GetMapping
    public String getCurrencies(Model model) {
        currencySvc.getCurrencies(model);
        return "index";
    }

    @GetMapping("/convert")
    public String getConvertCurreny(Model model,
        @RequestParam(name="fromCurrencyID") String from,
        @RequestParam(name="toCurrencyID") String to,
        @RequestParam(name="amount") String amount) {

        Double conversionRate = currencySvc.getConversion(from, to);
        Double convertedAmount = Double.parseDouble(amount) * conversionRate;

        Optional<MyCurrency> optFromCurrency = currencySvc.findCurrencyById(from);
        Optional<MyCurrency> optToCurrency = currencySvc.findCurrencyById(to);

        if(optFromCurrency.isEmpty() || optToCurrency.isEmpty())
            return "error";
        
        MyCurrency fromCurrency = optFromCurrency.get();
        MyCurrency toCurrency = optToCurrency.get();

        model.addAttribute("fromCurrencyName", fromCurrency.getCurrencyName());
        model.addAttribute("fromCurrencySymbol", fromCurrency.getCurrencySymbol());
        model.addAttribute("amount", amount);

        model.addAttribute("toCurrencyName", toCurrency.getCurrencyName());
        model.addAttribute("toCurrencySymbol", toCurrency.getCurrencySymbol());
        model.addAttribute("convertedAmount", convertedAmount);
        return "convert";
    }
}
