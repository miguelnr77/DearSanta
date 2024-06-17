package com.example.dearsanta.users.controllers;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String email, @RequestParam String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        return userService.registerUser(user);
    }

    @GetMapping("/confirm")
    public String confirmUser(@RequestParam String token) {
        userService.confirmUser(token);
        return "Gracias por confirmar tu cuenta.";
    }
}
