package com.example.springbootapp.service;

import com.example.springbootapp.DBdomain.Message;
import com.example.springbootapp.DBdomain.User;
import com.example.springbootapp.dto.MessageDTO;
import com.example.springbootapp.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class messageService {

    @Autowired
    private MessageRepo messageRepo;

    public Page<MessageDTO> getMessages(String filter, Pageable pageable, User user) {
        if (filter !=null && !filter.isEmpty()){
            return messageRepo.findByTag(filter, pageable, user);
        }else {
            return messageRepo.findAll(pageable, user);
        }
    }

    public Page<MessageDTO> getUserMessage(User currentUser, User author, Pageable pageable){
        return messageRepo.findByAuthor(currentUser,author, pageable);
    }

    public void like(Message message, User currentUser ){
        Set<User> likes = message.getLiked_users();
        Set<User> dislikes = message.getDisliked_users();
        if (likes.contains(currentUser)){
            likes.remove(currentUser);
        }else {
            likes.add(currentUser);
            dislikes.remove(currentUser);
        }
        messageRepo.save(message);
    }

    public void dislike(Message message, User currentUser){
        Set<User> dislikes = message.getDisliked_users();
        Set<User> likes = message.getLiked_users();
        if (dislikes.contains(currentUser)){
            dislikes.remove(currentUser);
        }else {
            dislikes.add(currentUser);
            likes.remove(currentUser);
        }
        messageRepo.save(message);
    }
}
