package com.balmis.proyecto.model;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Schema(description = "Modelo de Review", name="Review")

// JPA
@Entity
@Table(name = "reviews")

public class Review implements Serializable{

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID único del review", example = "0")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) 
    private int id;

    @Schema(description = "El review", example = "This is my review")
    @NotBlank(message = "El review es obligatorio")
    @Size(min = 1, max = 300, message = "El review no puede tener más de 300 caracteres")
    @Column(name = "review", nullable = false, unique = false)
    private String review;

    @Schema(description = "La fecha del review", example = "2015-01-11")
    @NotBlank(message = "La fecha es obligatoria")
    @Column(name = "review_date", nullable = false, unique = false)
    private Date review_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties("reviews")  
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    @JsonIgnoreProperties("reviews")  
    private Producto producto;
}
