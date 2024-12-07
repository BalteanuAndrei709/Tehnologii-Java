package org.laborator7.web;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.laborator7.entities.User;
import org.laborator7.service.UserService;

import java.io.Serializable;

@Getter
@Setter
@Named
@SessionScoped
public class AuthenticationBean implements Serializable {

    private String username;
    private String password;

    @Inject
    private UserService userService;

    public String login() {
        User user = userService.authenticate(username, password);
        if (user != null) {
            return switch (user.getRole().toString()) {
                case "STUDENT" -> "student.xhtml?faces-redirect=true";
                case "TEACHER" -> "teacher.xhtml?faces-redirect=true";
                case "ADMIN" -> "admin.xhtml?faces-redirect=true";
                default ->
                        "error.xhtml?faces-redirect=true";
            };
        } else {
            return "login.xhtml?faces-redirect=true";
        }
    }
}
