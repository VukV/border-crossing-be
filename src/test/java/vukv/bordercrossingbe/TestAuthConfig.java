package vukv.bordercrossingbe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import vukv.bordercrossingbe.domain.entities.user.User;
import vukv.bordercrossingbe.services.AuthService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TestAuthConfig {

    @Autowired
    private AuthService authService;

    public void authenticateUser() {
        authenticate("user@vuk.com");
    }

    public void authenticateAdmin() {
        authenticate("admin@vuk.com");
    }

    private void authenticate(String email) {
        User user = authService.getUserByEmail(email);

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, email, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void unauthenticated() {
        SecurityContextHolder.clearContext();
    }

}
