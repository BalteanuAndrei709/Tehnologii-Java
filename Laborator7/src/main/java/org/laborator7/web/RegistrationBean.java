package org.laborator7.web;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.laborator7.entities.Role;
import org.laborator7.service.UserService;

@Getter
@Setter
@Named
@RequestScoped
public class RegistrationBean {

    private String username;
    private String password;
    private String fullname;
    private Role role;

    @Inject
    private UserService userService;

    public void register() {
        userService.registerUser(username, fullname, password, role);
    }
}
