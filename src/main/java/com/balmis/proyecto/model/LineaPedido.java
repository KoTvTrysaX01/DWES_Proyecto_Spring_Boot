package com.balmis.proyecto.model;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
// @Entity // Places @Embeddable instead of @Entity. Somehow works?
@Embeddable
@Table(name = "lineas_pedido")
public class LineaPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Id
    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Schema(description = "Cantidad del producto del pedido", example = "1")
    @Min(value = 1, message = "La cantidad mínima es 1")
    @Column(name = "cantidad", nullable = false, unique = false)
    private int cantidad;

    @Schema(description = "Precio del producto del pedido", example = "151.99")
    @Min(value = 0, message = "El precio mínimo es 0.00")
    @Column(name = "precio", nullable = false, unique = false)
    private BigDecimal precio;
}
