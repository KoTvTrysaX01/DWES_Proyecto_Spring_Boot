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

import com.balmis.proyecto.model.Producto;
import com.balmis.proyecto.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Productos", description = "API para gestión de productos")
@RestController
@RequestMapping("/productos")
public class ProductoController {
    

    @Autowired
    private ProductoService productoService;

    // ***************************************************************************
    // CONSULTAS
    // ***************************************************************************
    // http://localhost:8080/proyecto/productos
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener todos los productos",
            description = "Retorna una lista con todos los productos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "productos obtenidos con éxito")
    })
    // ***************************************************************************    
    @GetMapping("")
    public ResponseEntity<List<Producto>> showproductos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productoService.findAll());
    }

    // http://localhost:8080/proyecto/productos/2
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener Producto por ID",
            description = "Retorna un Producto especifico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content())
    })
    // ***************************************************************************    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> detailsProducto(@PathVariable int id) {
        Producto producto = productoService.findById(id);

        if (producto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);  // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(producto);
        }
    }

    // http://localhost:8080/proyecto/productos/mayor/7
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener productos mayores de un ID",
            description = "Retorna una lista con todos los productos con ID mayor que un valor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "productos obtenidos con éxito")
    })
    // ***************************************************************************    
    @GetMapping("/mayor/{id}")
    public ResponseEntity<List<Producto>> showProductosMayores(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productoService.findByIdGrThan(id));
    }

    // http://localhost:8080/proyecto/productos/categoria/categoria_id
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener productos por un id de categoria",
            description = "Retorna una lista con los productos de una categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Productos obtenidos con éxito")
    })
    // ***************************************************************************    
    @GetMapping("/categoria/{categoria_id}")
    public ResponseEntity<List<Producto>> showProductosPorCategoria(@PathVariable int categoria_id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productoService.findByIdCategoria(categoria_id));
    }


    // http://localhost:8080/proyecto/productos/count
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener el número de productos existentes",
            description = "Retorna la cantidad de productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Número de productos obtenidos con éxito", content = @Content())
    })
    // ***************************************************************************    
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> countProductos() {

        ResponseEntity<Map<String, Object>> response = null;

        Map<String, Object> map = new HashMap<>();
        map.put("productos", productoService.count());

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
    // http://localhost:8080/proyecto/productos
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Crear una nueva producto",
            description = "Registra una nueva producto en el sistema con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creada con éxito", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content())
    })
    // ***************************************************************************
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createProducto(
            @Valid @RequestBody Producto producto) {

        ResponseEntity<Map<String, Object>> response;

        if (producto == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(map);
        } else {

            if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()
                    || producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()
                    || producto.getPrecio() == null
                    || producto.getCategoria() == null
                    ) {

                Map<String, Object> map = new HashMap<>();
                map.put("error", "Los campos 'Producto' y 'descripcion' son obligatorios");

                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(map);
            } else {
                System.out.println(producto);
                Producto productoPost = productoService.save(producto);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Producto creada con éxito");
                map.put("insertProducto", productoPost);

                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(map);
            }
        }

        return response;
    }

        // ****************************************************************************
    // UPDATE (PUT)
    // http://localhost:8080/proyecto/productos
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Actualizar un Producto existente",
            description = "Reemplaza completamente los datos de unu Producto identificado por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto actualizado con éxito", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content())
    })
    // ***************************************************************************    
    @PutMapping("")
    public ResponseEntity<Map<String, Object>> updateProducto(
            @Valid @RequestBody Producto productoUpdate) {

        ResponseEntity<Map<String, Object>> response;

        if (productoUpdate == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } else {
            int id = productoUpdate.getId();
            Producto existingProducto = productoService.findById(id);

            if (existingProducto == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("error", "Producto no encontrado");
                map.put("id", id);

                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            } else {

                // Actualizar campos si están presentes
                if (productoUpdate.getNombre() != null) {
                    existingProducto.setNombre(productoUpdate.getNombre());
                }
                if (productoUpdate.getDescripcion() != null) {
                    existingProducto.setDescripcion(productoUpdate.getDescripcion());
                }
                if (productoUpdate.getPrecio() != null) {
                    existingProducto.setPrecio(productoUpdate.getPrecio());
                }
                existingProducto.setStock(productoUpdate.isStock());
                if (productoUpdate.getImagen() != null) {
                    existingProducto.setImagen(productoUpdate.getImagen());
                }
                if (productoUpdate.getCategoria() != null) {
                    existingProducto.setCategoria(productoUpdate.getCategoria());
                }              

                Producto productoPut = productoService.save(existingProducto);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Producto actualizada con éxito");
                map.put("updatedProducto", productoPut);

                response = ResponseEntity.status(HttpStatus.OK).body(map);
            }
        }

        return response;
    }


    // ****************************************************************************
    // DELETE
    // http://localhost:8080/proyecto/productos/16
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Eliminar producto por ID",
            description = "Elimina un producto especifico del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto eliminado con éxito", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content())
    })
    // ***************************************************************************    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProducto(@PathVariable int id) {

        ResponseEntity<Map<String, Object>> response;

        Producto existingProducto = productoService.findById(id);
        if (existingProducto == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "Producto no encontrado");
            map.put("id", id);

            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        } else {

            productoService.deleteById(id);

            Map<String, Object> map = new HashMap<>();
            map.put("mensaje", "Producto eliminado con éxito");
            map.put("deletedProducto", existingProducto);

            response = ResponseEntity.status(HttpStatus.OK).body(map);
        }
        return response;
    }
}
