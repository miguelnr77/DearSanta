package com.example.dearsanta.users.controllers;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.models.VerificationToken;
import com.example.dearsanta.users.repositories.VerificationTokenRepository;
import com.example.dearsanta.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/confirm")
    public String confirmRegistration(@RequestParam("token") String token) {
        Optional<VerificationToken> verificationToken = tokenRepository.findByToken(token);

        if (verificationToken.isPresent()) {
            userService.enableUser(verificationToken.get().getUser());
            return "Your account has been successfully verified!";
        } else {
            return "Invalid token!";
        }
    }
}
