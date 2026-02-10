package com.balmis.proyecto.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Embeddable
public class LineaPedidoId implements Serializable{

    @OneToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id")
    public Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id")
    public Producto producto;
}
