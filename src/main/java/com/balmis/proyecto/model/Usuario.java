package com.balmis.proyecto.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// LOMBOK
@AllArgsConstructor
@NoArgsConstructor
@Data

// SWAGGER
@Schema(description = "Modelo de Usuario", name = "Usuario")

// JPA
@Entity
@Table(name = "users_security")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID único del usuario", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Schema(description = "Nombre del usuario", example = "Vadim")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 1, max = 50, message = "El nombre no puede tener más de 50 caracteres")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Schema(description = "Email del usuario", example = "vadim@balmis.com")
    @NotBlank(message = "El email es obligatorio")
    @Size(min = 1, max = 100, message = "El email no puede tener más de 100 caracteres")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Schema(description = "Password del usuario encriptada", example = "$2a$10$fzcGgF.8xODz7ptkmZC")
    @NotBlank(message = "El password es obligatorio")
    @Size(min = 1, max = 200, message = "El password no puede tener más de 200 caracteres")
    @Column(name = "password", nullable = false, unique = false)
    private String password;

    @Schema(description = "Activar permisos de Administrador", example = "false")
    @Column(name = "administrador", nullable = false, unique = false)
    private boolean administrador;

    @Schema(description = "Activar permisos de Usuario", example = "true")
    @Column(name = "usuario", nullable = false, unique = false)
    private boolean usuario;

    @Schema(description = "Activar permisos de Invitado", example = "true")
    @Column(name = "invitado", nullable = false, unique = false)
    private boolean invitado;

    @Schema(description = "Activar el usuario", example = "true")
    @Column(name = "activado", nullable = false, unique = false)
    private boolean activado;

}
