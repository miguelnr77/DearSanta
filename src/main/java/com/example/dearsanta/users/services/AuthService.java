package com.example.dearsanta.users.services;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean login(String email, String password, HttpServletRequest request) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password) && user.get().isEnabled()) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user.get());
            return true;
        }
        return false;
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
    }

    public User getAuthenticatedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute("user");
        }
        return null;
    }

    public boolean isAuthenticated(HttpServletRequest request) {
        return getAuthenticatedUser(request) != null;
    }
}
