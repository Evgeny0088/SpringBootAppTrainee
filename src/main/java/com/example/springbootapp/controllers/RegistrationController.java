package com.example.springbootapp.controllers;

import com.example.springbootapp.DBdomain.User;
import com.example.springbootapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String RegisterPage(){
        return "registration";
    }

    @PostMapping("/registration")
    public String userRegister(
                    @RequestParam("passwordConfirm") String passwordConfirm,
                    @Valid User user,
                    BindingResult bindingResult,
                    Model model){
        boolean isPasswordConfirmEmpty = passwordConfirm.isEmpty();
        if (user.getPassword()!=null && !user.getPassword().equals(passwordConfirm)){
            model.addAttribute("passwordConfirmError", "password is not confirmed");
            return "registration";
        }
        if (bindingResult.hasErrors() || isPasswordConfirmEmpty){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("user",user);
            return "registration";
        }
        if (!userService.addUser(user)){
            String message = String.format("user with name %s is exists!",user.getUsername());
            model.addAttribute("message",message);
            return "registration";
        }
        model.addAttribute("message", null);
        return "redirect:/login";
    }

    @GetMapping("/activation/{code}")
    public String mailActivation(Model model, @PathVariable String code){
        boolean isActivated = userService.userActivation(code);
        if (isActivated){
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User is succsefully activated!");
        }else {
            model.addAttribute("messageType","danger");
            model.addAttribute("message", "Activation failed(:");
        }
        return "login";
    }

}
