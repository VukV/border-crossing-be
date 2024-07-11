package vukv.bordercrossingbe.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vukv.bordercrossingbe.domain.dtos.auth.RegisterRequest;
import vukv.bordercrossingbe.domain.dtos.user.UserDto;
import vukv.bordercrossingbe.domain.entities.user.User;
import vukv.bordercrossingbe.domain.mappers.UserMapper;
import vukv.bordercrossingbe.exception.exceptions.BadRequestException;
import vukv.bordercrossingbe.exception.exceptions.ForbiddenException;
import vukv.bordercrossingbe.exception.exceptions.NotFoundException;
import vukv.bordercrossingbe.repositories.UserRepository;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User login(String email, String password){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Email not found"));

        if (!user.isActive()) {
            throw new ForbiddenException("Account is not activated");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Incorrect password");
        }
        user.setAccessToken(generateToken(user));
        log.info("{} logged in", user.getEmail());

        return user;
    }

    public UserDto register(RegisterRequest registerRequest) {
        if(!registerRequest.getPassword().equals(registerRequest.getRepeatPassword())) {
            throw new BadRequestException("Passwords are not the same");
        }

        User user = UserMapper.INSTANCE.fromRegisterRequest(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        log.info("Registering a new user: {}", user.getEmail());

        return UserMapper.INSTANCE.toDto(userRepository.save(user));
    }

    private String generateToken(User user) {
        long tokenExpirationTime = 30L * 24 * 60 * 60 * 1000; // 1 month
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

}
