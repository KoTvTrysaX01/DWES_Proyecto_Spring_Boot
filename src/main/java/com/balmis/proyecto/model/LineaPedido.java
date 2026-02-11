package com.balmis.proyecto.model;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// LOMBOK
@AllArgsConstructor
@NoArgsConstructor
@Data

// SWAGGER
@Schema(description = "Modelo de LineaPedido", name = "LineaPedido")

// JPA
@Entity
@Table(name = "lineas_pedido")
public class LineaPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    // @EmbeddedId 
    // @OneToOne(optional = false, orphanRemoval = true)
    // @JoinColumn(name = "id_pedido", nullable = false)
    // private Pedido pedido;

    // @EmbeddedId 
    // @OneToOne(optional = false, orphanRemoval = true)
    // @JoinColumn(name = "id_producto", nullable = false)
    // private Producto producto;

    @Schema(description = "EmbeddedId de la categoria. Se compone de Pedido y Producto")
    @EmbeddedId
    private LineaPedidoId lineaPedidoId;

    @Schema(description = "La cantidad del producto del pedido", example = "0")
    @Min(value = 0, message = "La cantidad mínima es 0")
    @Column(name = "cantidad", nullable = false, unique = false)
    private int cantidad;

    @Schema(description = "El precio del producto del pedido", example = "151.99")
    @Min(value = 0, message = "El precio mínimo es 0.00")
    @Column(name = "precio", nullable = false, unique = false)
    private BigDecimal precio;
}
