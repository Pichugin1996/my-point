package com.weblinestudio.mypoint.dto;

import com.weblinestudio.mypoint.entity.user.Role;
import com.weblinestudio.mypoint.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String email;
    private String username;
    private String password;
    private String passwordConfirm;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean checkboxDirector;

    public User transformUser() {
        User user = new User();
        user.setUsername(username);

        Set<Role> roles = new HashSet<>();
        if (checkboxDirector) {
            roles.add(Role.DIRECTOR);
            roles.add(Role.USER);
            user.setRoles(roles);
        }

        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);

        return user;
    }

}
