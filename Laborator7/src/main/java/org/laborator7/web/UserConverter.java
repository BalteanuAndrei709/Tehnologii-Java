package org.laborator7.web;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import org.laborator7.entities.User;
import org.laborator7.service.UserService;

import jakarta.inject.Inject;

@FacesConverter(value = "userConverter", managed = true)
public class UserConverter implements Converter<User> {

    @Inject
    private UserService userService;

    @Override
    public User getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return userService.findById(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, User user) {
        if (user == null || user.getId() == null) {
            return "";
        }
        return user.getId().toString();
    }
}
