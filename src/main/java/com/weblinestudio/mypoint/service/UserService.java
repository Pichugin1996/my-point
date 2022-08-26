package com.weblinestudio.mypoint.service;

import com.weblinestudio.mypoint.entity.user.Role;
import com.weblinestudio.mypoint.entity.user.User;
import com.weblinestudio.mypoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRoles() == null) {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.USER);
            user.setRoles(roles);
        }

        user.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

        user.setActive(true);

        userRepository.save(user);

    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(new User());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(new User());
    }

    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone).orElse(new User());
    }
}
