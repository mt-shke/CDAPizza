package com.example.demoapi.controller;

import com.example.demoapi.dto.RegisterRequest;
import com.example.demoapi.model.User;
import com.example.demoapi.repository.UserRepository;
import com.example.demoapi.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    private RegisterRequest registerRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("michel123");
        registerRequest.setPassword("password123");
        registerRequest.setEmail("michel@email.com");
        registerRequest.setRole("client");

        user = new User();
        user.setUsername("michel123");
        user.setPassword("hashedPassword");
        user.setEmail("michel@email.com");
        user.setRole("client");
    }

    // ===== TESTS REGISTER =====

    @Test
    void register_success() {
        when(userRepository.findByUsername("michel123")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("michel@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = authController.register(registerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Inscription réussie", response.getBody());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_username_deja_pris() {
        when(userRepository.findByUsername("michel123")).thenReturn(Optional.of(user));

        ResponseEntity<?> response = authController.register(registerRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ce nom d'utilisateur est déjà pris", response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_email_deja_utilise() {
        when(userRepository.findByUsername("michel123")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("michel@email.com")).thenReturn(Optional.of(user));

        ResponseEntity<?> response = authController.register(registerRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cette adresse email est déjà utilisée", response.getBody());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_role_defaut_client() {
        registerRequest.setRole(null);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

        authController.register(registerRequest);

        verify(userRepository).save(argThat(u -> "client".equals(u.getRole())));
    }

    @Test
    void register_password_est_hashe() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");

        authController.register(registerRequest);

        verify(userRepository).save(argThat(u -> "hashedPassword".equals(u.getPassword())));
        verify(passwordEncoder, times(1)).encode("password123");
    }

    // ===== TESTS LOGIN =====

    @Test
    void login_success() {
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByUsername("michel123")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user)).thenReturn("fake.jwt.token");

        jakarta.servlet.http.HttpServletRequest request = mock(jakarta.servlet.http.HttpServletRequest.class);
        Map<String, String> body = Map.of("username", "michel123", "password", "password123");

        ResponseEntity<?> response = authController.login(request, body);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("token"));
        verify(jwtUtil, times(1)).generateToken(user);
    }

    @Test
    void login_identifiants_invalides() {
        doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        jakarta.servlet.http.HttpServletRequest request = mock(jakarta.servlet.http.HttpServletRequest.class);
        Map<String, String> body = Map.of("username", "michel123", "password", "mauvais_password");

        ResponseEntity<?> response = authController.login(request, body);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Identifiants invalides", response.getBody());
        verify(jwtUtil, never()).generateToken(any());
    }

    @Test
    void login_user_introuvable_apres_auth() {
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(userRepository.findByUsername("michel123")).thenReturn(Optional.empty());

        jakarta.servlet.http.HttpServletRequest request = mock(jakarta.servlet.http.HttpServletRequest.class);
        Map<String, String> body = Map.of("username", "michel123", "password", "password123");

        assertThrows(RuntimeException.class, () -> authController.login(request, body));
    }
}