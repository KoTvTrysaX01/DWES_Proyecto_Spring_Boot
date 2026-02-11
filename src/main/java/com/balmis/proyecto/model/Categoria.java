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
@Schema(description = "Modelo de Categoria", name="Categoria")

// JPA
@Entity
@Table(name = "categorias")

public class Categoria implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Schema(description = "ID único de la categoria", example = "0")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) 
    private int id;

    @Schema(description = "El nombre de la categoria", example = "Cookies")
    @NotBlank(message = "El nombre de la categoria es obligatorio")
    @Size(min=1, max=50, message = "El nombre de la categoria no puede tener más de 50 caracteres")
    @Column(name = "categoria", nullable = false, unique = true) 
    private String categoria;

    @Schema(description = "La descripcion de la categoria", example = "Categoria de Cookies")
    @NotBlank(message = "La descripcion de la categoria es obligatoria")
    @Size(min=1, max=200, message = "La descripcion de la categoria no puede tener más de 200 caracteres")
    @Column(name = "descripcion", nullable = false, unique = false) 
    private String descripcion;

    @Schema(description = "El irectorio de la imagen de la categoria", example = "./directorio/cookies.png")
    @Size(min=1, max=100, message = "El directorio de la imagen la categoria no puede tener más de 100 caracteres")
    @Column(name = "imagen", nullable = true, unique = false) 
    private String imagen;
}
