package com.example.springbootapp.dto;

import com.example.springbootapp.DBdomain.Message;
import com.example.springbootapp.DBdomain.User;
import com.example.springbootapp.utils.messageUtils;

public class MessageDTO {
    private Long id;
    private String text;
    private String tag;
    private String filename;
    private User author;
    private Long likes;
    private Long dislikes;
    private Boolean meLiked;
    private Boolean medisliked;

    public MessageDTO(Message message, Long likes, Long dislikes, Boolean meLiked, Boolean disliked) {
        this.id = message.getId();
        this.tag = message.getTag();
        this.text = message.getText();
        this.filename = message.getFilename();
        this.author = message.getAuthor();
        this.likes = likes;
        this.meLiked = meLiked;
        this.dislikes = dislikes;
        this.medisliked = disliked;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getTag() {
        return tag;
    }

    public String getFilename() {
        return filename;
    }

    public User getAuthor() {
        return author;
    }

    public Long getLikes() {
        return likes;
    }

    public Boolean getMeLiked() {
        return meLiked;
    }

    public String getAuthorName(){
        return messageUtils.getAuthorName(author);
    }

    public Long getDislikes() {
        return dislikes;
    }

    public Boolean getMedisliked() {
        return medisliked;
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
                "id=" + id +
                ", author=" + author +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                ", meLiked=" + meLiked +
                ", medisliked=" + medisliked +
                '}';
    }
}
