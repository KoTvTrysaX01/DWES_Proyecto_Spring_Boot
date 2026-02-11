
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
                    .requestMatchers("/").permitAll()                                       // Acceso público a "/"
                    .requestMatchers("/swagger-ui.html**").permitAll()                      // Acceso a la documentacion de Swagger
                    .requestMatchers("/api/auth/**").permitAll()                            // Acceso público a Identificación
                    .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()          // Acceso público a GET de "/categorias"
                    .requestMatchers("/categorias/**").hasRole("ADMIN")               // Acceso privado (ADMIN) a GET POST PUT DELETE de "/categorias"
                    .requestMatchers(HttpMethod.GET, "/productos/**").permitAll()           // Acceso público a GET de "/productos"
                    .requestMatchers("/productos/**").hasRole("ADMIN")                 // Acceso privado (ADMIN) a GET POST PUT DELETE de "/productos"
                    .requestMatchers(HttpMethod.POST, "/mensajes/**").permitAll()            // Acceso público a POST de "/mensajes"
                    .requestMatchers("/mensajes/**").hasRole("ADMIN")                  // Acceso privado a GET POST PUT DELETE de "/mensajes"
                    .requestMatchers(HttpMethod.GET, "/pedidos/**").hasRole("USER")     // Acceso privado (USER) a GET de "/pedidos"
                    .requestMatchers(HttpMethod.POST, "/pedidos/**").hasRole("USER")    // Acceso privado (USER) a POST de "/pedidos"
                    .requestMatchers("/pedidos/**").hasRole("ADMIN")                    // Acceso privado (ADMIN) a GET POST PUT DELETE de "/productos"
                    .requestMatchers(HttpMethod.POST, "/usuarios/**").permitAll()            // Acceso publico a POST de "/usuarios"
                    .requestMatchers("/usuarios/**").hasRole("ADMIN")                   // Acceso privado (ADMIN) a GET POST PUT DELETE de "/usuarios"
                    .requestMatchers(HttpMethod.GET, "/reviews").permitAll()                 // Acceso público a GET de "/reviews"
                    .requestMatchers(HttpMethod.POST, "/reviews").hasRole("USER")       // Acceso privado (USER) a POST de "/reviews"
                    .requestMatchers("/reviews/**").hasRole("ADMIN")                    // Acceso privado (ADMIN) a GET POST PUT DELETE de "/reviews"
                    .requestMatchers("/lineaspedido/**").hasRole("ADMIN")               // Acceso privado (ADMIN) a GET POST PUT DELETE de "/lineaspedido"
                    .requestMatchers("/h2-console/**").hasRole("ADMIN")                 // Acceso identificado a consola H2           
                    .anyRequest().denyAll()                                                               // Acceso DENEGADO al resto & Comentar para acceder a la documentacion de swagger?
                )

                .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/h2-console/**") 
                    .disable()
                )
                .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .headers(headers -> headers
                    .frameOptions(frame -> frame
                        .sameOrigin() 
                    )
                )                  
                .formLogin(form -> 
                        form.disable()
                ) 
                .httpBasic(httpBasic -> Customizer
                        .withDefaults()
                );
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

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