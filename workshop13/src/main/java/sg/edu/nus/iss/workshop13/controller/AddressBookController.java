package sg.edu.nus.iss.workshop13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import sg.edu.nus.iss.workshop13.model.Contact;
import sg.edu.nus.iss.workshop13.util.Contacts;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AddressBookController {

    private Logger logger = Logger.getLogger(AddressBookController.class.getName());

    @Autowired
    private ApplicationArguments applicationArguments;

    // end point to forward to a generate.html page
    // root of the path is set therefore when user access this
    // web app it will always default to this method
    @GetMapping("/")
    public String showContactForm(Model model) {

        logger.log(Level.INFO, "Show the contact form");
        model.addAttribute("contactObj", new Contact());

        return "contactPage";
    }

    @GetMapping("/getContact/{contactId}")
    public String getContact( @PathVariable(value="contactId") String contactId, Model model){

        logger.log(Level.INFO, "contactId: " + contactId);

        Contacts ct = new Contacts();
        ct.getContactById(model, contactId, applicationArguments);

        return "showContact";
    }
    
    @PostMapping("/contact")
    public String proccessContact(@ModelAttribute Contact contact, Model model, HttpServletResponse httpResponse) {

        logger.log(Level.INFO, "Id: " + contact.getId());
        logger.log(Level.INFO, "Name: " + contact.getName());
        logger.log(Level.INFO, "Email: " + contact.getEmail());
        logger.log(Level.INFO, "Phone: " + contact.getPhone());

        Contacts c = new Contacts();
        c.saveContact(contact, model, applicationArguments);

        httpResponse.setStatus(HttpStatus.CREATED.value());
        model.addAttribute("httpResponse", String.valueOf(httpResponse.getStatus()));
        return "showContact";
    }
}
