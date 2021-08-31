package com.example.springbootapp.controllers;

import com.example.springbootapp.DBdomain.Role;
import com.example.springbootapp.DBdomain.User;
import com.example.springbootapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users",userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEdit(@PathVariable User user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam Map<String,String> form,
            @RequestParam ("username") String username,
            @RequestParam ("user_id") User user){
        userService.saveUser(user,form,username);
        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String profileEdit(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("email", user.getEmail());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
        return "profile";
    }

    @PostMapping("/profile")
    public String profileUpdate(
            @AuthenticationPrincipal User user,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email){
        userService.profileUpdate(user, username, password, email);
        return "redirect:/users/profile";
    }

    @GetMapping("/subscribe/{user}")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") User user) {
        userService.subscribe(user, currentUser);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") User user) {
        userService.unsubscribe(user, currentUser);
        return "redirect:/user-messages/" + user.getId();
    }

    @GetMapping("/{type}/{user}")
    public String subscriptions(
            @PathVariable("user") User user,
            @PathVariable("type") String type,
            Model model) {
        model.addAttribute("type", type);
        if (Objects.equals(type, "subscriptions")){
            Set<User> subscriptions = user.getSubscriptions();
            model.addAttribute("users", subscriptions);
        } else {
            Set<User> subscribers = user.getSubscribers();
            model.addAttribute("users", subscribers);
        }
        return "subscriptions";
    }

}
