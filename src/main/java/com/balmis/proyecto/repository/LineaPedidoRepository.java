package com.balmis.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.balmis.proyecto.model.LineaPedido;
import com.balmis.proyecto.model.LineaPedidoId;
import com.balmis.proyecto.model.Usuario;

public interface LineaPedidoRepository extends JpaRepository<LineaPedido, LineaPedidoId> {

    // Buscar - Todos
    @Query(value = "SELECT * FROM lineas_pedido", nativeQuery = true)
    List<LineaPedido> findSqlAll();
    
    // Buscar - Por ID de Pedido
    @Query(value = "SELECT * FROM lineas_pedido WHERE id_pedido = :id_pedido", nativeQuery = true)
    List<LineaPedido> findSqlByIdPedido(@Param("id_pedido") int id_pedido);

    // Buscar - Por ID de Producto
    @Query(value = "SELECT * FROM lineas_pedido WHERE id_producto = :id_producto", nativeQuery = true)
    List<LineaPedido> findSqlByIdProducto(@Param("id_producto") int id_producto);

    // Buscar - Contar todos
    @Query(value = "SELECT COUNT(*) as lineas_pedido FROM lineas_pedido", nativeQuery = true)
    Long countSql();
    

    @Query(value = "SELECT users_security.* FROM ((lineas_pedido INNER JOIN pedidos ON :id_pedido = pedidos.id) INNER JOIN users_security ON pedidos.user_id = users_security.id)", nativeQuery = true)
    Usuario findSqlUserByIdPedido(@Param("id_pedido") int id_pedido);

    // @Query(value = "SELECT * FROM lineas_pedido WHERE lineaPedidoId.id = :id_pedido AND id_producto = :id_producto", nativeQuery = true)
    // LineaPedido findSqlByIds(@Param("lineaPedidoId") LineaPedidoId lineaPedidoId.);
    
}
