package com.balmis.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.balmis.proyecto.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    
    // Optional<Categoria> findByCategoria(String categoria);

    // Buscar - Todos
    @Query(value = "SELECT * FROM categorias", nativeQuery = true)
    List<Categoria> findSqlAll();
    
    // Buscar - Por ID
    @Query(value = "SELECT * FROM categorias WHERE id = :id", nativeQuery = true)
    Categoria findSqlByIdCategory(@Param("id") int id);

    // Buscar - Contar todos
    @Query(value = "SELECT COUNT(*) as cantidad FROM categorias", nativeQuery = true)
    Long countSql();    

    // Buscar - Todos menores que Num
    @Query(value = "SELECT * FROM categorias WHERE id > :num", nativeQuery = true)
    List<Categoria> findSqlByIdGrThan(@Param("num") int num);
}
