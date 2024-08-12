package com.scmpro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scmpro.entities.Call;
import com.scmpro.entities.User;
import com.scmpro.forms.UserForm;
import com.scmpro.helper.Message;
import com.scmpro.helper.MessageType;
import com.scmpro.servcies.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String home(Model m) {
        m.addAttribute("title", "Home Page");
        m.addAttribute("name", "kartik more");
        m.addAttribute("project", "SCM");
        return "home";
    }

    // about
    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }

    // about
    @GetMapping("/services")
    public String getServices() {
        return "services";
    }

    // contact
    @GetMapping("/contact")
    public String getContact(Model model) {
        Call call = new Call();
        model.addAttribute("call", call);
        return "contact";
    }

    // login
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    // signup
    @GetMapping("/signup")
    public String getSignup(Model model) {
        model.addAttribute("title", "Sign Up Page");
        UserForm userForm = new UserForm();

        model.addAttribute("userForm", userForm);

        return "signup";
    }

    @PostMapping("/do-register")
    public String doRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
            HttpSession session) {

        // Validate form
        if (rBindingResult.hasErrors()) {
            return "signup";
        }

        // Creating user And Adding Value From userForm ==> user
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic(
                "https://imgs.search.brave.com/aPzzOrFrseVkMghXZNnZWBH2FnWWgD3Q9BEDdwJlysg/rs:fit:500:0:0:0/g:ce/aHR0cHM6Ly9hc3Nl/dHMtZ2xvYmFsLndl/YnNpdGUtZmlsZXMu/Y29tLzVlYzdkYWQy/ZTZmNjI5NWE5ZTJh/MjNkZC81ZWRmYTdj/NjYwNGM3N2IxYjRi/ZDY1OGFfcHJvZmls/ZXBob3RvNS5qcGVn");

        // Saving User Into database
        userService.saveUser(user);

        // Giving Message to View
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message", message);

        return "redirect:/signup";

    }

}
