package org.example.modules.auth.controller;

import jakarta.validation.Valid;
import org.example.modules.auth.dto.AuthResponse;
import org.example.modules.auth.dto.LoginRequest;
import org.example.modules.auth.dto.RegisterRequest;
import org.example.modules.auth.service.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
/**
 * Controlador REST para manejar las peticiones de Auth.
 */
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    /**
     * Método login.
     */
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    /**
     * Método register.
     */
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registrar(request));
    }
}

