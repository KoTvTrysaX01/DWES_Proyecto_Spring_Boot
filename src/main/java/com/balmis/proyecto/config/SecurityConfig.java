
package com.balmis.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.balmis.proyecto.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
        System.out.println("CustomUserDetailsService inyectado: " + (customUserDetailsService != null));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/").permitAll()                               // Acceso público a "/"
                    .requestMatchers("/api/auth/**").permitAll()                    // Acceso público a Identificación
                    .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()
                    .requestMatchers("/categoria/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/productos/**").permitAll()
                    .requestMatchers("/productos/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/mensajes/**").permitAll()
                    .requestMatchers("/mensajes/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/pedidos/**").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/pedidos/**").hasRole("USER")
                    .requestMatchers("/pedidos/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll()
                    .requestMatchers("/usuarios/**").hasRole("ADMIN")
                    .requestMatchers("/lineaspedido/**").hasRole("ADMIN")              
                    .requestMatchers("/h2-console/**").hasRole("ADMIN")             // Acceso identificado a consola H2           
                    .anyRequest().denyAll()                                         // Acceso DENEGADO al resto
                )
                // Inicio de Configuraciones adicionales para H2
                .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/h2-console/**") // Desactiva CSRF para H2
                    .disable()
                )
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Crea sesión solo si es necesario (por defecto)
                )
                .headers(headers -> headers
                    .frameOptions(frame -> frame
                        .sameOrigin() // Permite iframes del mismo origen (necesario para H2)
                    )
                )    
                // Fin de Configuraciones adicionales para H2                
                .formLogin(form -> 
                        form.disable()  // desactivamos formulario login por defecto
                ) 
                .httpBasic(httpBasic -> Customizer
                        .withDefaults()
                );
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {          // ← muy recomendable añadirlo aquí

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}