package com.scmpro.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scmpro.entities.Contact;
import com.scmpro.entities.User;
import com.scmpro.forms.ContactForm;
import com.scmpro.forms.ContactSearchForm;
import com.scmpro.helper.AppConstant;
import com.scmpro.helper.EmailHelper;
import com.scmpro.helper.Message;
import com.scmpro.helper.MessageType;
import com.scmpro.servcies.ContactService;
import com.scmpro.servcies.ImageService;
import com.scmpro.servcies.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    private List<Contact> contactsByUser;

    // Add Contact view
    @RequestMapping("/add")
    public String showContact(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contact", contactForm);
        return "user/show_contact_form";
    }

    @PostMapping("/add")
    public String addContact(@ModelAttribute ContactForm contactForm,
            Authentication authentication) {

        // Validate Form

        // Getting username
        String username = EmailHelper.getEmailOfLogedUser(authentication);
        User user = userService.getUserByEmail(username);

        // Genrating Random FileName
        String fileName = UUID.randomUUID().toString();

        // Fetting File Url
        String fileUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);

        // Convert ContactForm ==> Contact
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedInLink(contactForm.getLinkedinLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setPicture(fileUrl);
        contact.setCloudnaryImagePublicId(fileName);
        contact.setUser(user);

        // Saving contact
        contactService.save(contact);

        // Redirecting to the same page
        return "redirect:/user/contacts/add";
    }

    // view Contact
    @RequestMapping("/all")
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Authentication authentication, Model model) {

        String username = EmailHelper.getEmailOfLogedUser(authentication);

        User user = userService.getUserByEmail(username);
        Page<Contact> pageContacts = contactService.getContactsByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstant.PAGE_SIZE);

        return "user/contacts";

    }

    @RequestMapping("/search")
    public String searchHandler(

            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        var user = userService.getUserByEmail(EmailHelper.getEmailOfLogedUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstant.PAGE_SIZE);

        return "/user/search";
    }

    // detete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable("contactId") String contactId,
            HttpSession session) {
        contactService.deletecontact(contactId);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact is Deleted successfully !! ")
                        .type(MessageType.green)
                        .build()

        );

        return "redirect:/user/contacts/all";
    }

    // update contact form view
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
            @PathVariable("contactId") String contactId,
            Model model) {

        var contact = contactService.getContact(contactId);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setWebsiteLink(contact.getLinkedInLink());
        // contactForm.setContactImage(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
            @Valid @ModelAttribute ContactForm contactForm,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        System.out.println(contactForm);

        // update the contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

        var con = contactService.getContact(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedinLink());

        System.out.println(con);

        // process image:

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudnaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            // contactForm.setContactImage(imageUrl)

        } else {
            System.out.println("empty");
        }

        contactService.update(con);

        // Giving Message to View
        Message message = Message.builder().content("Contact Updated SuccessFully!!").type(MessageType.green).build();
        session.setAttribute("message", message);

        return "redirect:/user/contacts/view/" + contactId;
    }

}
