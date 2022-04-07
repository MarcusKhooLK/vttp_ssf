package sg.edu.nus.iss.workshop12.controller;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.nus.iss.workshop12.exception.InvalidNumberException;
import sg.edu.nus.iss.workshop12.model.Generate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class GenerateController {

    private Logger logger = LoggerFactory.getLogger(GenerateController.class);

    // end point to forward to a generate.html page
    // root of the path is set therefore when user access this
    // web app it will always default to this method
    @GetMapping("/")
    public String showGenerationForm(Model model) {
        Generate generate = new Generate();
        model.addAttribute("generateObj", generate);
        return "generatePage";
    }

    @PostMapping("/generate")
    public String generateNumbers(@ModelAttribute Generate generate, Model model) {
        try {
            int numbersToGenerate = generate.getNumberVal();
            logger.info("Numbers to generate: " + numbersToGenerate);

            if (numbersToGenerate > 30) {
                throw new InvalidNumberException();
            }

            Random random = new Random();
            Set<Integer> uniqueGeneratedNumbers = new HashSet<Integer>();

            while (uniqueGeneratedNumbers.size() < numbersToGenerate) {
                int numGenerated = random.nextInt(30) + 1;
                uniqueGeneratedNumbers.add(numGenerated);
            }

            model.addAttribute("uniqueGeneratedNumbers", uniqueGeneratedNumbers.toArray());
            model.addAttribute("numbersToGenerate", numbersToGenerate);

        } catch (InvalidNumberException e) {
            model.addAttribute("errorMessage", "Invalid number!");
            return "error";
        }

        return "result";
    }
}
