package com.balmis.proyecto.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private final AuthenticationManager authManager;

    public AuthController(AuthenticationManager authManager) {
        this.authManager = authManager;
        // Este método permite conectar este controller con los datos de UserDetails de SecurityConfig
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(        
            @RequestBody Map<String, String> credentials,
            HttpServletRequest request) {

        String username = credentials.get("username");
        String password = credentials.get("password");
        
        
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        // Muy importante: guardar en la sesión
        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context);

        // Obtener datos del usuario autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Map<String, String> map = Map.of(
                "message", "Login realizado con éxito",
                "user", userDetails.getUsername(),
                "roles", userDetails.getAuthorities().toString());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(map);
    }
     
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Map<String, String> map = Map.of("message", "Sesión cerrada");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(map);
    }
     
    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> user(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Map<String, String> map;
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            map = Map.of(
                    "message", "Usuario no identificado"
            );
        } else {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            map = Map.of(
                    "username", userDetails.getUsername(),
                    "roles", userDetails.getAuthorities().toString()
            );
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(map);
    }
}

