
// package com.balmis.proyecto.config;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// import com.balmis.proyecto.service.CustomUserDetailsService;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final CustomUserDetailsService customUserDetailsService;

//     @Autowired
//     public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
//         this.customUserDetailsService = customUserDetailsService;
//     }    
    
//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/").permitAll()          // Acceso público a "/"
//                 .requestMatchers("/acerca").permitAll()    // Acceso público a "/acerca"
//                 .requestMatchers("/catalogo/**").permitAll()  // Acceso público a "/catalogo"
//                 .requestMatchers("/login").permitAll()     // Acceso público a "/login"
//                 .requestMatchers("/productos").hasAnyRole("ADMIN","USER")  // Acceso restringido
//                 .requestMatchers("/usuarios").hasRole("ADMIN")  // Acceso restringido
//                 .requestMatchers("/h2-console","/h2-console/**").hasRole("ADMIN")  // Acceso restringido
//                 .anyRequest().authenticated()              // Acceso indentificado a "/secure"
//             )
//             // Inicio de Configuraciones adicionales para H2
//             .csrf(csrf -> csrf
//                 .ignoringRequestMatchers("/h2-console/**") // Desactiva CSRF para H2
//             )
//             .sessionManagement(session -> session
//                 .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Crea sesión solo si es necesario (por defecto)
//             )
//             .headers(headers -> headers
//                 .frameOptions(frame -> frame
//                     .sameOrigin() // Permite iframes del mismo origen (necesario para H2)
//                 )
//             )    
//             // Fin de Configuraciones adicionales para H2
//             .formLogin(form -> form                     
//                 .loginPage("/login")              // Usar la plantilla /login.html 
//                 .defaultSuccessUrl("/", true)     // Si se indentifica 
//             )
//             .logout(logout -> logout                    // Configuración del logout
//                 .logoutUrl("/logout")                   // URL que dispara el logout (POST por defecto "/logout")
//                 .logoutSuccessUrl("/")                  // Redirige a la página principal después del logout
//                 .invalidateHttpSession(true)            // Invalida la sesión HTTP
//                 .clearAuthentication(true)              // Limpia la autenticación
//                 .deleteCookies("JSESSIONID")            // Elimina la cookie de sesión (opcional-recomendable)
//             );  
//         return http.build();
//     }
    
    
// /*
//     @Bean
//     public UserDetailsService userDetailsService() {
//         UserDetails user = User
//                 .withUsername("user")
//                 .password(passwordEncoder().encode("1234"))
//                 .password("{noop}1234")
//                 .roles("USER")
//                 .build();
//         UserDetails admin = User
//                 .withUsername("admin")
//                 //.password(passwordEncoder().encode("5678"))
//                 .password("{noop}5678")
//                 .roles("USER","ADMIN")
//                 .build();

//         return new InMemoryUserDetailsManager(user, admin);
//     }
// */

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

// }
