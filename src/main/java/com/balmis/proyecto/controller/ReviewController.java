package com.balmis.proyecto.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balmis.proyecto.model.Review;
import com.balmis.proyecto.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Reviews", description = "API para gestión de reviews")
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    

    @Autowired
    private ReviewService reviewService;

    // ***************************************************************************
    // CONSULTAS
    // ***************************************************************************
    // http://localhost:8080/proyecto/reviews
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener todos los reviews",
            description = "Retorna una lista con todos los reviews disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "reviews obtenidos con éxito")
    })
    // ***************************************************************************    
    @GetMapping("")
    public ResponseEntity<List<Review>> showReviews() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.findAll());
    }

    // http://localhost:8080/proyecto/reviews/2
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener review por ID",
            description = "Retorna un review especifico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review encontrado"),
        @ApiResponse(responseCode = "404", description = "Review no encontrado", content = @Content())
    })
    // ***************************************************************************    
    @GetMapping("/{id}")
    public ResponseEntity<Review> detailsReview(@PathVariable int id) {
        Review review = reviewService.findById(id);

        if (review == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);  // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(review);
        }
    }

    // http://localhost:8080/proyecto/reviews/mayor/7
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener reviews mayores de un ID",
            description = "Retorna una lista con todos los reviews con ID mayor que un valor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "reviews obtenidos con éxito")
    })
    // ***************************************************************************    
    @GetMapping("/mayor/{id}")
    public ResponseEntity<List<Review>> showReviewsMayores(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.findByIdGrThan(id));
    }

    // http://localhost:8080/proyecto/reviews/user/7
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener reviews de un usuario",
            description = "Retorna una lista con todos los reviews de un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "reviews obtenidos con éxito")
    })
    // ***************************************************************************    
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Review>> showReviewUsuario(@PathVariable int user_id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.findByUserId(user_id));
    }

    // http://localhost:8080/proyecto/reviews/producto/7
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener reviews de un producto",
            description = "Retorna una lista con todos los reviews de un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "reviews obtenidos con éxito")
    })
    // ***************************************************************************    
    @GetMapping("/producto/{producto_id}")
    public ResponseEntity<List<Review>> showReviewProducto(@PathVariable int producto_id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.findByProductoId(producto_id));
    }


    // http://localhost:8080/proyecto/reviews/count
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener el número de reviews existentes",
            description = "Retorna la cantidad de reviews")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Número de reviews obtenidos con éxito", content = @Content())
    })
    // ***************************************************************************    
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> countReviews() {

        ResponseEntity<Map<String, Object>> response = null;

        Map<String, Object> map = new HashMap<>();
        map.put("reviews", reviewService.count());

        response = ResponseEntity
                .status(HttpStatus.OK)
                .body(map);

        return response;
    }


    // ***************************************************************************
    // ACTUALIZACIONES
    // ***************************************************************************
    // ****************************************************************************
    // INSERT (POST)    
    // http://localhost:8080/proyecto/reviews
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Crear un nuev review",
            description = "Registra un nuev review en el sistema con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Review creado con éxito", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content())
    })
    // ***************************************************************************
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createReview(
            @Valid @RequestBody Review review) {

        ResponseEntity<Map<String, Object>> response;

        if (review == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(map);
        } else {

            if (review.getReview() == null || review.getReview().trim().isEmpty()
                    || review.getReview_date() == null
                    || review.getUsuario() == null
                    ) {

                Map<String, Object> map = new HashMap<>();
                map.put("error", "Los campos 'review', 'review_date' y 'usuario' son obligatorios");

                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(map);
            } else {
                System.out.println(review);
                Review reviewPost = reviewService.save(review);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Review creado con éxito");
                map.put("insertReview", reviewPost);

                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(map);
            }
        }

        return response;
    }

    // ****************************************************************************
    // UPDATE (PUT)
    // http://localhost:8080/proyecto/reviews
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Actualizar un review existente",
            description = "Reemplaza completamente los datos de unu Review identificado por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Review actualizado con éxito", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Review no encontrado", content = @Content())
    })
    // ***************************************************************************    
    @PutMapping("")
    public ResponseEntity<Map<String, Object>> updateReview(
            @Valid @RequestBody Review reviewUpdate) {

        ResponseEntity<Map<String, Object>> response;

        if (reviewUpdate == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } else {
            int id = reviewUpdate.getId();
            Review existingReview = reviewService.findById(id);

            if (existingReview == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("error", "Review no encontrado");
                map.put("id", id);

                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            } else {

                // Actualizar campos si están presentes
                if (reviewUpdate.getReview() != null) {
                    existingReview.setReview(reviewUpdate.getReview());
                }
                if (reviewUpdate.getReview_date() != null) {
                    existingReview.setReview_date(reviewUpdate.getReview_date());
                }
                if (reviewUpdate.getUsuario() != null) {
                    existingReview.setUsuario(reviewUpdate.getUsuario());
                }              

                Review reviewPut = reviewService.save(existingReview);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Review actualizado con éxito");
                map.put("updatedReview", reviewPut);

                response = ResponseEntity.status(HttpStatus.OK).body(map);
            }
        }

        return response;
    }


    // ****************************************************************************
    // DELETE
    // http://localhost:8080/proyecto/reviews/16
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Eliminar review por ID",
            description = "Elimina un review especifico del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Review eliminado con éxito", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Review no encontrado", content = @Content())
    })
    // ***************************************************************************    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteReview(@PathVariable int id) {

        ResponseEntity<Map<String, Object>> response;

        Review existingReview = reviewService.findById(id);
        if (existingReview == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "Review no encontrado");
            map.put("id", id);

            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        } else {

            reviewService.deleteById(id);

            Map<String, Object> map = new HashMap<>();
            map.put("mensaje", "Review eliminado con éxito");
            map.put("deletedReview", existingReview);

            response = ResponseEntity.status(HttpStatus.OK).body(map);
        }
        return response;
    }
}



