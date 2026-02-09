package com.balmis.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.balmis.proyecto.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
        // Buscar - Todos
    @Query(value = "SELECT * FROM pedidos", nativeQuery = true)
    List<Pedido> findSqlAll();
    
    // Buscar - Por ID
    @Query(value = "SELECT * FROM pedidos WHERE id = :id", nativeQuery = true)
    Pedido findSqlByIdPedido(@Param("id") int id);

    // Buscar - Por ID de Usuario
    @Query(value = "SELECT * FROM pedidos WHERE user_id = :user_id", nativeQuery = true)
    List<Pedido> findSqlByIdUsuario(@Param("user_id") int user_id);

    // Buscar - Contar todos
    @Query(value = "SELECT COUNT(*) as cantidad FROM pedidos", nativeQuery = true)
    Long countSql();    

    // Buscar - Todos menores que Num
    @Query(value = "SELECT * FROM pedidos WHERE id > :num", nativeQuery = true)
    List<Pedido> findSqlByIdGrThan(@Param("num") int num);
}
