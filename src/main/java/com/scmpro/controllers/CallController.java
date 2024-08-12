package com.scmpro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scmpro.entities.Call;
import com.scmpro.helper.Message;
import com.scmpro.helper.MessageType;
import com.scmpro.servcies.CallService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class CallController {

    @Autowired
    private CallService callService;
    
    @PostMapping("/addcall")
    public String getMethodName(@Valid @ModelAttribute Call callForm, BindingResult bindingResult  , HttpSession session) {

        // Validate
        if (bindingResult.hasErrors())
            return "contact";

        // call Form ==> Call object 
        Call call = new Call();
        call.setFirstName(callForm.getFirstName());
        call.setLastName(callForm.getLastName());
        call.setPhoneNumber(callForm.getPhoneNumber());
        call.setEmail(callForm.getEmail());
        call.setProfession(callForm.getProfession());
        call.setMessage(callForm.getMessage());

        // Save call object into db
        callService.save(call);

        // Giving Message to View
        Message message = Message.builder().content("Message Send Successfully").type(MessageType.green).build();
        session.setAttribute("message", message);
        
        System.out.println("Call Form: " + callForm);
        return "redirect:/contact";
    }
    
    


}
