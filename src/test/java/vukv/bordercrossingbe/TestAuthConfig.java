package vukv.bordercrossingbe;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TestAuthConfig {

    public void authenticateUser() {
        authenticate("USER", "user@vuk.com", "e49fcab5-d45b-4556-9d91-14e58177fea6");
    }

    public void authenticateAdmin() {
        authenticate("ADMIN", "admin@vuk.com", "e40fcab5-d45b-4567-9d91-14e58178fea6");
    }

    private void authenticate(String role, String email, String userId) {
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);

        Map<String, Object> details = new HashMap<>();
        details.put("userId", userId);
        authentication.setDetails(details);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void unauthenticated() {
        SecurityContextHolder.clearContext();
    }

}
