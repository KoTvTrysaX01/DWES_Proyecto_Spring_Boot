package com.balmis.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.balmis.proyecto.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    
    // Buscar - Todos
    @Query(value = "SELECT * FROM reviews", nativeQuery = true)
    List<Review> findSqlAll();
    
    // Buscar - Por ID
    @Query(value = "SELECT * FROM reviews WHERE id = :id", nativeQuery = true)
    Review findSqlByIdReview(@Param("id") int id);

    // Buscar - Por ID de Usuario
    @Query(value = "SELECT * FROM reviews WHERE user_id = :user_id", nativeQuery = true)
    List<Review> findSqlByIdUsuario(@Param("user_id") int user_id);

    // Buscar - Contar todos
    @Query(value = "SELECT COUNT(*) as cantidad FROM reviews", nativeQuery = true)
    Long countSql();    

    // Buscar - Todos menores que Num
    @Query(value = "SELECT * FROM reviews WHERE id > :num", nativeQuery = true)
    List<Review> findSqlByIdGrThan(@Param("num") int num);
}
