package com.example.springbootapp.controllers;

import com.example.springbootapp.DBdomain.Message;
import com.example.springbootapp.DBdomain.User;
import com.example.springbootapp.dto.MessageDTO;
import com.example.springbootapp.exceptions.NotFoundException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
public class MessageController {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private messageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping("/user-messages/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") User user,
            @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
            Model model){
        Page<MessageDTO> page;
        page = messageService.getUserMessage(currentUser,user, pageable);
        String url = String.format("/user-messages/%d",user.getId());
        model.addAttribute("page",page);
        model.addAttribute("url", url);
        model.addAttribute("userChannel", user);
        model.addAttribute("iscurrentuser", user.equals(currentUser));
        model.addAttribute("SubscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("SubscribersCount", user.getSubscribers().size());
        model.addAttribute("ifSubscriber", user.getSubscribers().contains(currentUser));
        return "userMessages";
    }

    @GetMapping("/user-messages/{user}/{message_id}")
    public String userMessagedit(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") User user,
            @PathVariable("message_id") Message message,
            Model model) throws NotFoundException{
        if (user.getId()==null){
            throw new NotFoundException(user.getId(), user.getUsername());
        }
        model.addAttribute("message", message);
        model.addAttribute("currentUser",currentUser.equals(user));
        return "EditMessage";
    }

    @PostMapping("/user-messages/{user}/{message_id}")
    public String editMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") Long user,
            @PathVariable("message_id") @Valid Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file) throws IOException, NotFoundException {
        if (message.getAuthor() == currentUser){
            if (text!=null && !text.isEmpty()) {
                message.setText(text);
            }
            if (tag!=null && !tag.isEmpty()) {
                message.setTag(tag);
            }
            userService.savePicture(message,file);
            messageRepo.save(message);
        }
        if (user==null) {
            throw new NotFoundException(user,"user");
        }
        return "redirect:/user-messages/" + user;
    }

    @GetMapping("/message/{message_id}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("message_id") Message message,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer) {
        messageService.like(message, currentUser);
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams().entrySet()
                .forEach(pair->redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
        return "redirect:" + components.getPath();
    }

    @GetMapping("/message/{message_id}/dislike")
    public String dislike(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("message_id") Message message,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer) {
        messageService.dislike(message, currentUser);
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams().entrySet()
                .forEach(pair->redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
        return "redirect:" + components.getPath();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user-messages/delete/{id}")
    public String deleteMessage(
                    @PathVariable Long id,
                    Model model)
                    throws NotFoundException {
        Optional<Message> message = messageRepo.findById(id);
        if (message.isEmpty()) {
            throw new NotFoundException(id,"message");
        }
        messageRepo.deleteById(id);
        model.addAttribute("message_id", id);
        return "deleted_message_page";
    }
}
