package vukv.bordercrossingbe.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vukv.bordercrossingbe.domain.entities.user.User;
import vukv.bordercrossingbe.exception.exceptions.AuthorizationException;


public class AuthUtils {

    public static User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return (User) authentication.getPrincipal();
        } else {
            throw new AuthorizationException("User not logged in");
        }
    }

}
