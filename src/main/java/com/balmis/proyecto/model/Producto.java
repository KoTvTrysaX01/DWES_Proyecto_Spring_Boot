package com.balmis.proyecto.model;


import java.io.Serializable;
import java.math.BigDecimal;

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
import jakarta.validation.constraints.Min;
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
@Schema(description = "Modelo de Producto", name="Producto")

// JPA
@Entity
@Table(name = "productos")
public class Producto implements Serializable{

    private static final long serialVersionUID = 1L; 

    @Schema(description = "ID único del producto", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) 
    private int id;

    @Schema(description = "Nombre del producto", example = "Ordenador portátil")
    @NotBlank(message = "El nombre es obligatoria")
    @Size(min=1, max=50, message = "El nombre no puede tener más de 50 caracteres")
    @Column(name = "nombre", nullable = false, unique = true) 
    private String nombre;

    @Schema(description = "Descripcion del producto", example = "Ordenador portátil muy muy bueno")
    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min=1, max=200, message = "La descripcion no puede tener más de 200 caracteres")
    @Column(name = "descripcion", nullable = false, unique = false) 
    private String descripcion;

    @Schema(description = "Precio del producto", example = "151.99")
    @Min(value = 0, message = "El precio mínimo es 0.00")
    @Column(name = "precio", nullable = false, unique = false) 
    private BigDecimal precio;

    @Schema(description = "El stock del producto", example = "true")
    @Column(name = "stock", nullable = false, unique = false)
    private boolean stock;

    @Schema(description = "Directorio de la imagen del producto", example = "./directorio/imagen.png")
    @Size(min=1, max=100, message = "El Directorio de la imagen no puede tener más de 100 caracteres")
    @Column(name = "imagen", nullable = true, unique = false) 
    private String imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    @JsonIgnoreProperties("productos")  
    private Categoria categoria;
}
