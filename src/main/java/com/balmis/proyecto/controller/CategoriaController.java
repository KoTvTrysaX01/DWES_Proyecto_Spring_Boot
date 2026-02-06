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

import com.balmis.proyecto.model.Categoria;
import com.balmis.proyecto.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Categorias", description = "API para gestión de categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    

    @Autowired
    private CategoriaService categoriaService;

    // ***************************************************************************
    // CONSULTAS
    // ***************************************************************************
    // http://localhost:8080/proyecto/categorias
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener todas las categorias",
            description = "Retorna una lista con todas las categorias disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorias obtenidas con éxito")
    })
    // ***************************************************************************    
    @GetMapping("")
    public ResponseEntity<List<Categoria>> showCategorias() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoriaService.findAll());
    }

    // http://localhost:8080/proyecto/categorias/2
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener categoria por ID",
            description = "Retorna una categoria específica basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content())
    })
    // ***************************************************************************    
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> detailsCategoria(@PathVariable int id) {
        Categoria categoria = categoriaService.findById(id);

        if (categoria == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);  // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(categoria);
        }
    }

    // http://localhost:8080/proyecto/categorias/mayor/7
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener categorias mayores de un ID",
            description = "Retorna una lista con todas las categorias con ID mayor que un valor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorias obtenidas con éxito")
    })
    // ***************************************************************************    
    @GetMapping("/mayor/{id}")
    public ResponseEntity<List<Categoria>> showCategoriasMayores(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoriaService.findByIdGrThan(id));
    }


        // http://localhost:8080/proyecto/categorias/count
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener el número de categorias existentes",
            description = "Retorna la cantidad de categorias")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Número de categorias obtenidas con éxito", content = @Content())
    })
    // ***************************************************************************    
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> countCategorias() {

        ResponseEntity<Map<String, Object>> response = null;

        Map<String, Object> map = new HashMap<>();
        map.put("categorias", categoriaService.count());

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
    // http://localhost:8080/proyecto/categorias
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Crear una nueva categoria",
            description = "Registra una nueva categoria en el sistema con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria creada con éxito", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content())
    })
    // ***************************************************************************
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createCategoria(
            @Valid @RequestBody Categoria categoria) {

        ResponseEntity<Map<String, Object>> response;

        if (categoria == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(map);
        } else {

            if (categoria.getCategoria() == null || categoria.getCategoria().trim().isEmpty()
                    || categoria.getDescripcion() == null || categoria.getDescripcion().trim().isEmpty()
                    || categoria.getImagen() == null || categoria.getImagen().trim().isEmpty()
                    ) {

                Map<String, Object> map = new HashMap<>();
                map.put("error", "Los campos 'categoria' y 'descripcion' son obligatorios");

                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(map);
            } else {
                System.out.println(categoria);
                Categoria categoriaPost = categoriaService.save(categoria);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Categoria creada con éxito");
                map.put("insertCategoria", categoriaPost);

                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(map);
            }
        }

        return response;
    }

        // ****************************************************************************
    // UPDATE (PUT)
    // http://localhost:8080/proyecto/categorias
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Actualizar una categoria existente",
            description = "Reemplaza completamente los datos de unu categoria identificada por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria actualizada con éxito", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content())
    })
    // ***************************************************************************    
    @PutMapping("")
    public ResponseEntity<Map<String, Object>> updateCategoria(
            @Valid @RequestBody Categoria categoriaUpdate) {

        ResponseEntity<Map<String, Object>> response;

        if (categoriaUpdate == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } else {
            int id = categoriaUpdate.getId();
            Categoria existingCategoria = categoriaService.findById(id);

            if (existingCategoria == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("error", "Categoria no encontrada");
                map.put("id", id);

                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            } else {

                // Actualizar campos si están presentes
                if (categoriaUpdate.getCategoria() != null) {
                    existingCategoria.setCategoria(categoriaUpdate.getCategoria());
                }
                if (categoriaUpdate.getDescripcion() != null) {
                    existingCategoria.setDescripcion(categoriaUpdate.getDescripcion());
                }
                if (categoriaUpdate.getImagen() != null) {
                    existingCategoria.setImagen(categoriaUpdate.getImagen());
                }                

                Categoria categoriaPut = categoriaService.save(existingCategoria);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Categoria actualizada con éxito");
                map.put("updatedCategoria", categoriaPut);

                response = ResponseEntity.status(HttpStatus.OK).body(map);
            }
        }

        return response;
    }


    // ****************************************************************************
    // DELETE
    // http://localhost:8080/proyecto/categorias/16
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Eliminar categoria por ID",
            description = "Elimina una categoria específica del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria eliminada con éxito", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content())
    })
    // ***************************************************************************    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategoria(@PathVariable int id) {

        ResponseEntity<Map<String, Object>> response;

        Categoria existingCategoria = categoriaService.findById(id);
        if (existingCategoria == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "Categoria no encontrada");
            map.put("id", id);

            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        } else {

            categoriaService.deleteById(id);

            Map<String, Object> map = new HashMap<>();
            map.put("mensaje", "Categoria eliminada con éxito");
            map.put("deletedCategoria", existingCategoria);

            response = ResponseEntity.status(HttpStatus.OK).body(map);
        }
        return response;
    }
}


