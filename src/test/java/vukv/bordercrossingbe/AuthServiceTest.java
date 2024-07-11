package vukv.bordercrossingbe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import vukv.bordercrossingbe.domain.dtos.auth.RegisterRequest;
import vukv.bordercrossingbe.domain.dtos.user.UserDto;
import vukv.bordercrossingbe.domain.entities.user.Role;
import vukv.bordercrossingbe.domain.entities.user.User;
import vukv.bordercrossingbe.exception.exceptions.BadRequestException;
import vukv.bordercrossingbe.repositories.UserRepository;
import vukv.bordercrossingbe.services.AuthService;

import static org.junit.jupiter.api.Assertions.*;

@Sql("/sql/auth.sql")
@Import(TestcontainersConfiguration.class)
@SpringBootTest
public class AuthServiceTest extends TestConfig {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRegister() {
        RegisterRequest request = RegisterRequest.builder()
                .email("testuser@vuk.com")
                .password("123456")
                .repeatPassword("123456")
                .firstName("Test")
                .lastName("User")
                .build();

        UserDto createdUser = authService.register(request);

        assertNotNull(createdUser);
        assertEquals(request.getEmail(), createdUser.getEmail());
        assertEquals(request.getFirstName(), createdUser.getFirstName());
        assertEquals(request.getLastName(), createdUser.getLastName());
        assertEquals(Role.USER, createdUser.getRole());

        User retrievedUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals(request.getEmail(), retrievedUser.getEmail());
    }

    @Test
    public void testLogin() {
        User loggedUser = authService.login("user@vuk.com", "123456");

        assertNotNull(loggedUser);
        assertEquals("host", loggedUser.getEmail());
        assertNotNull(loggedUser.getAccessToken());
    }

    @Test
    public void testInvalidLogin() {
        assertThrows(BadRequestException.class, () -> authService.login("host", "badPass"));
    }

}
