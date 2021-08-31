package com.example.springbootapp.DBdomain;

import com.example.springbootapp.utils.messageUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "please fill the message!")
    @Length(max = 2048, message = "Message too long, more than 2Kb")
    private String text;
    @Length(max = 255, message = "Tag too long, more than 256 symbols")
    private String tag;
    private String filename;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToMany
    @JoinTable(name = "message_likes",joinColumns = @JoinColumn(name = "message_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "liked_users_id",referencedColumnName = "id"))
    private Set<User> liked_users = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "message_dislikes",joinColumns = @JoinColumn(name = "message_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "disliked_users_id",referencedColumnName = "id"))
    private Set<User> disliked_users = new HashSet<>();

    public Message(){}

    public Message(String text, String tag, User author){
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthorName(){
        return messageUtils.getAuthorName(author);
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Set<User> getLiked_users() {
        return liked_users;
    }
    public void setLiked_users(Set<User> liked_users) {
        this.liked_users = liked_users;
    }

    public Set<User> getDisliked_users() {
        return disliked_users;
    }
    public void setDisliked_users(Set<User> disliked_users) {
        this.disliked_users = disliked_users;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
