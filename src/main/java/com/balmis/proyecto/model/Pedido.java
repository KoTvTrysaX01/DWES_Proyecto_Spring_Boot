package com.balmis.proyecto.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Schema(description = "Modelo de Pedido", name="Pedido")

// JPA
@Entity
@Table(name = "pedidos")

public class Pedido implements Serializable{

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID único del pedido", example = "0")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true) 
    private int id;

    @Schema(description = "Los productos del pedido", example = "Ice cream, Smoothie")
    @NotBlank(message = "Los productos del pedido son obligatorios")
    @Size(min = 1, max = 300, message = "Los productos del pedido no pueden tener más de 300 caracteres")
    @Column(name = "pedido", nullable = false, unique = false)
    private String pedido;

    @Schema(description = "El precio total del pedido", example = "151.99")
    @Min(value = 0, message = "El precio total mínimo es 0.00")
    @Column(name = "precio_total", nullable = false, unique = false) 
    private BigDecimal precio_total;

    @Schema(description = "El telefono del usuario", example = "123456789")
    @NotBlank(message = "El telefono es obligatorios")
    @Size(min = 1, max = 10, message = "El telefono no puede tener más de 10 caracteres")
    @Column(name = "tel", nullable = false, unique = false)
    private String tel;

    @Schema(description = "La direccion del usuario", example = "calle MiCalle")
    @NotBlank(message = "La direccion es obligatoria")
    @Size(min=1, max=150, message = "La direccion no puede tener más de 150 caracteres")
    @Column(name = "direccion", nullable = false, unique = false) 
    private String direccion;

    @Schema(description = "La fecha del pedido", example = "2015-01-11")
    @NotBlank(message = "La fecha del pedido es obligatoria")
    @Column(name = "pedido_date", nullable = false, unique = false)
    private Date pedido_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties("pedidos")  
    private Usuario usuario;
}
