package com.example.springbootapp.service;

import com.example.springbootapp.DBdomain.Message;
import com.example.springbootapp.DBdomain.Role;
import com.example.springbootapp.DBdomain.User;
import com.example.springbootapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Value("${upload.path_dev}")
    private String uploaded_path;

    @Value("${hostname}")
    private String hostname;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("User not found!");
        }
        return userRepo.findByUsername(username);
    }

    private void sendMessage(User user) {
        if (user.getEmail()!=null && !user.getEmail().isEmpty()){
            String message = String.format("Hello %s\n" +
                    "Please follow this link to confirm your mail:\n" +
                    "http://%s/activation/%s",
                    user.getUsername(),
                    hostname,
                    user.getActivationCode());
            mailSender.send(user.getEmail(),"Activation code", message);
        }
    }

    public void savePicture(@Valid Message message, @RequestParam("file") MultipartFile file) throws IOException {
        if (file !=null && !file.getOriginalFilename().isEmpty()){
            File dir = new File(uploaded_path);
            if (!dir.exists()){
                dir.mkdir();
            }
            String resulFileName = UUID.randomUUID() + file.getOriginalFilename();
            file.transferTo(new File(uploaded_path + "/" + resulFileName));
            message.setFilename(resulFileName);
        }
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public void saveUser(User user, Map<String,String> form, String username){
        user.getRoles().clear();
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        for (String k: form.keySet()) {
            if (roles.contains(k))
                user.getRoles().add(Role.valueOf(k));
        }
        userRepo.save(user);
    }

    public boolean addUser(User user){
        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB!=null){
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        sendMessage(user);
        return true;
    }

    public boolean userActivation(String code){
        User user = userRepo.findByActivationCode(code);
        if (user==null){
            return false;
        }
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

    public void profileUpdate(User user, String username, String password, String email) {
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();
        String usernameDB = user.getUsername();
        boolean isEmailChanged = (email!=null && !email.isEmpty() && !email.equals(userEmail))
                || (user.getEmail()!=null && userEmail.equals(email));
        boolean isPasswordChanged = (password!=null && !password.equals(userPassword));
        boolean isUsernameChanged = (username!=null && !username.equals(usernameDB));
        if (isEmailChanged){
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
        }
        if (isPasswordChanged){
            user.setPassword(passwordEncoder.encode(password));
        }
        if (isUsernameChanged){
            user.setUsername(username);
        }
        userRepo.save(user);

        if (isEmailChanged){
            sendMessage(user);
        }
    }

    public void subscribe(User user, User currentUser) {
        user.getSubscribers().add(currentUser);
        userRepo.save(user);
    }

    public void unsubscribe(User user, User currentUser) {
        user.getSubscribers().remove(currentUser);
        userRepo.save(user);
    }
}
