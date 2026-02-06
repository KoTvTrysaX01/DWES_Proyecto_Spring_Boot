package com.balmis.proyecto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.balmis.proyecto.model.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
    

    // Optional<Mensaje> findByDescrip(String titulo);
    
    // Buscar - Todos
    @Query(value = "SELECT * FROM mensajes", nativeQuery = true)
    List<Mensaje> findSqlAll();
    
    // Buscar - Por ID
    @Query(value = "SELECT * FROM mensajes WHERE id = :id", nativeQuery = true)
    Mensaje findSqlByIdMensaje(@Param("id") int id);

    // Buscar - Por Titulo de Mensaje
    @Query(value = "SELECT * FROM mensajes WHERE titulo = :titulo", nativeQuery = true)
    Mensaje findSqlByTitulo(@Param("titulo") String titulo);

    // Buscar - Contar todos
    @Query(value = "SELECT COUNT(*) as cantidad FROM mensajes", nativeQuery = true)
    Long countSql();    

    // Buscar - Todos menores que Num
    @Query(value = "SELECT * FROM mensajes WHERE id > :num", nativeQuery = true)
    List<Mensaje> findSqlByIdGrThan(@Param("num") int num);
}
