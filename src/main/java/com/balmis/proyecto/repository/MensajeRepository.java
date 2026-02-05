package com.balmis.proyecto.repository;

import com.balmis.proyecto.model.Mensaje;
import com.balmis.proyecto.model.Producto;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
    

    Optional<Producto> findByDescrip(String titulo);
    
    // Buscar - Todos
    @Query(value = "SELECT * FROM productos", nativeQuery = true)
    List<Producto> findSqlAll();
    
    // Buscar - Por ID
    @Query(value = "SELECT * FROM productos WHERE id = :id", nativeQuery = true)
    Producto findSqlByIdUser(@Param("id") int id);

    // Buscar - Por ID de Categoria
    @Query(value = "SELECT * FROM productos WHERE titulo = :titulo", nativeQuery = true)
    Producto findSqlByIdCategoria(@Param("titulo") String titulo);

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
