package sg.edu.nus.iss.workshop14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.workshop14.model.Contact;
import sg.edu.nus.iss.workshop14.service.ContactsRedis;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AddressBookController {

    private Logger logger = Logger.getLogger(AddressBookController.class.getName());

    @Autowired
    ContactsRedis service;

    // end point to forward to a generate.html page
    // root of the path is set therefore when user access this
    // web app it will always default to this method
    @GetMapping("/")
    public String showContactForm(Model model) {

        logger.log(Level.INFO, "Show the contact form");
        model.addAttribute("contact", new Contact());

        return "contactPage";
    }

    @GetMapping("/getContact/{contactId}")
    public String getContact( @PathVariable(value="contactId") String contactId, Model model){

        logger.log(Level.INFO, "contactId: " + contactId);

        Contact ctc = service.findById(contactId);
        
        logger.log(Level.INFO, "name: " + ctc.getName());
        logger.log(Level.INFO, "email: " + ctc.getEmail());

        model.addAttribute("contact", ctc);

        return "showContact";
    }

    @GetMapping("/contact")
    public String getAllContact(Model model, @RequestParam(name="startIndex") String startIndex) {
        logger.log(Level.INFO, startIndex);
        List<Contact> result = service.findAll(Integer.parseInt(startIndex));

        model.addAttribute("allContacts", result);

        return "listContact";
    }
    
    @PostMapping("/contact")
    public String proccessContact(@ModelAttribute Contact contact, Model model, HttpServletResponse httpResponse) {

        logger.log(Level.INFO, "Id: " + contact.getId());
        logger.log(Level.INFO, "Name: " + contact.getName());
        logger.log(Level.INFO, "Email: " + contact.getEmail());
        logger.log(Level.INFO, "Phone: " + contact.getPhone());

        service.save(contact);

        model.addAttribute("contact", contact);

        return "showContact";
    }
}
