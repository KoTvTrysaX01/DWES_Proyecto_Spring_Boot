package com.balmis.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.balmis.proyecto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    
    // Buscar - Todos
    @Query(value = "SELECT * FROM productos", nativeQuery = true)
    List<Producto> findSqlAll();
    
    // Buscar - Por ID
    @Query(value = "SELECT * FROM productos WHERE id = :id", nativeQuery = true)
    Producto findSqlByIdProducto(@Param("id") int id);

    // Buscar - Por ID de Categoria
    @Query(value = "SELECT * FROM productos WHERE categoria_id = :categoria_id", nativeQuery = true)
    List<Producto> findSqlByIdCategoria(@Param("categoria_id") int categoria_id);

    // Buscar - Contar todos
    @Query(value = "SELECT COUNT(*) as cantidad FROM productos", nativeQuery = true)
    Long countSql();    

    // Buscar - Todos 
    @Query(value = "SELECT * FROM productos WHERE id > :num", nativeQuery = true)
    List<Producto> findSqlByIdGrThan(@Param("num") int num);

    // Buscar - Contar stock
    @Query(value = "SELECT COUNT(*) FROM productos WHERE stock = TRUE", nativeQuery = true)
    Long countStockSql();
}
