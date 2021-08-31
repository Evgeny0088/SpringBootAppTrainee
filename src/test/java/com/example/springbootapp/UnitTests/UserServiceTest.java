package com.example.springbootapp.UnitTests;

import com.example.springbootapp.DBdomain.Role;
import com.example.springbootapp.DBdomain.User;
import com.example.springbootapp.repositories.UserRepo;
import com.example.springbootapp.service.MailSender;
import com.example.springbootapp.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;

@SpringBootTest
@TestPropertySource(value = "/application-test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void addUserTest() {
        User user = new User();
        user.setEmail("some@mail.com");
        user.setPassword(passwordEncoder.encode("1"));
        boolean isCreated = userService.addUser(user);

        Assertions.assertTrue(isCreated);
        Assertions.assertNotNull(user.getActivationCode());
        Assertions.assertEquals(user.getRoles(), Collections.singleton(Role.USER));
        Assertions.assertEquals(passwordEncoder.encode("1"), user.getPassword());

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1)).send(
                ArgumentMatchers.eq(user.getEmail()),
                ArgumentMatchers.matches("Activation code"),
                ArgumentMatchers.contains("Please follow this link to confirm your mail"));
    }

    @Test
    void addUserFailedTest() {
        User user = new User();
        user.setUsername("John");
        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername(user.getUsername());
        boolean isCreated = userService.addUser(user);
        Assertions.assertFalse(isCreated);
        Mockito.verify(userRepo, Mockito.times(0)).save(user);
        Mockito.verify(mailSender, Mockito.times(0)).send(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString());
    }

    @Test
    void userActivationTest() {
        User user = new User();
        String activated = "activated";
        user.setActivationCode(activated);
        Mockito.doReturn(user)
                .when(userRepo)
                .findByActivationCode(activated);
        boolean isActivated = userService.userActivation(activated);
        Assertions.assertTrue(isActivated);
        Assertions.assertNull(user.getActivationCode());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    void userActivationFailedTest() {
        boolean isActivationFailed = userService.userActivation("wrong activation call");
        Assertions.assertFalse(isActivationFailed);
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }


}
