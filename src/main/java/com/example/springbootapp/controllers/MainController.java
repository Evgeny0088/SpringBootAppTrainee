package com.example.springbootapp.controllers;

import com.example.springbootapp.DBdomain.Message;
import com.example.springbootapp.DBdomain.User;
import com.example.springbootapp.dto.MessageDTO;
import com.example.springbootapp.repositories.MessageRepo;
import com.example.springbootapp.service.UserService;
import com.example.springbootapp.service.messageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private messageService messageService;

    @GetMapping("/")
    public String helloPage(@RequestParam(value = "name",required = false,defaultValue = "to all")String myname,
                            Map<String,Object> model){
        model.put("name",myname);
        return "HelloPage";
    }

    @GetMapping("/main")
    public String showAllMessages(@RequestParam(required = false, defaultValue = "") String filter,
                                  @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                                  @AuthenticationPrincipal User currentUser,
                                  Model model){
        Page<MessageDTO> page = messageService.getMessages(filter, pageable, currentUser);
        model.addAttribute("url","/main");
        model.addAttribute("page",page);
        model.addAttribute("filter", filter);
        return "MainPage";
    }

    @PostMapping("/main")
    public String postmessage(
                            @AuthenticationPrincipal User author,
                            @Valid Message message,
                            BindingResult bindingResult,
                            Model model,
                            @RequestParam("file") MultipartFile file,
                            @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable)
                            throws IOException {
        message.setAuthor(author);
        if (bindingResult.hasErrors()){
            Map<String,String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
        }
        else{
            userService.savePicture(message,file);
            messageRepo.save(message);
            model.addAttribute("message",null);
        }
        Page<Message> page;
        page = messageRepo.findAll(pageable);
        model.addAttribute("page",page);
        model.addAttribute("current_user_id", author.getId());
        return "redirect:/main";
    }

}
