package com.balmis.proyecto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// LOMBOK
@AllArgsConstructor
@NoArgsConstructor
@Data

// SWAGGER
@Schema(description = "Modelo de Mensaje", name="Mensaje")

// JPA
@Entity
@Table(name = "mensajes")

public class Mensaje implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Schema(description = "ID único del mensaje", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) 
    private int id;

    @Schema(description = "Titulo del mensaje", example = "Hola")
    @NotBlank(message = "El titulo del mensaje es obligatorio")
    @Size(min=1, max=30, message = "El titulo del mensaje no puede tener más de 30 caracteres")
    @Column(name = "titulo", nullable = false, unique = false) 
    private String titulo;
    
    @Schema(description = "Mensaje del usuario", example = "Hola, como estas?")
    @NotBlank(message = "El mensaje es obligatorio")
    @Size(min=1, max=300, message = "El mensaje no puede tener más de 300 caracteres")
    @Column(name = "mensaje", nullable = false, unique = false) 
    private String mensaje;
    
    @Schema(description = "Email del usuario", example = "vadim@balmis.com")
    @NotBlank(message = "El email es obligatorio")
    @Size(min = 1, max = 100, message = "El email no puede tener más de 100 caracteres")
    @Column(name = "email", nullable = false, unique = false)
    private String email;


    @Schema(description = "Fecha del mensaje", example = "01-11-2015")
    @NotBlank(message = "La fecha es obligatorio")
    @Column(name = "post_date", nullable = false, unique = false)
    private Date post_date;
}
