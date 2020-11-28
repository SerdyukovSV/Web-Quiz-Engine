package engine.service;

import engine.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUsers {

    public User getAuthenticationUsers() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
