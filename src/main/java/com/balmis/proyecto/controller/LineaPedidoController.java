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

import com.balmis.proyecto.model.LineaPedido;
import com.balmis.proyecto.service.LineaPedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Lineas de Pedido", description = "API para gestión de lineas de pedido")
@RestController
@RequestMapping("/lineaspedido")
public class LineaPedidoController {
    

    @Autowired
    private LineaPedidoService lineaPedidoService;

    // ***************************************************************************
    // CONSULTAS
    // ***************************************************************************
    // http://localhost:8080/proyecto/lineaspedido
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener todas las categorias",
            description = "Retorna una lista con todas las categorias disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categorias obtenidas con éxito")
    })
    // ***************************************************************************    
    @GetMapping("")
    public ResponseEntity<List<LineaPedido>> showCategorias() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lineaPedidoService.findAll());
    }

    // http://localhost:8080/proyecto/lineaspedido/pedido/2
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener categoria por ID",
            description = "Retorna una categoria específica basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content())
    })
    // ***************************************************************************    
    @GetMapping("/pedido/{id}")
    public ResponseEntity<List<LineaPedido>> detailsPedido(@PathVariable int id) {
        List<LineaPedido> lineasPedido = lineaPedidoService.findByIdPedido(id);

        if (lineasPedido == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);  // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lineasPedido);
        }
    }

        // http://localhost:8080/proyecto/lineaspedido/producto/2
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener categoria por ID",
            description = "Retorna una categoria específica basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoria no encontrada", content = @Content())
    })
    // ***************************************************************************    
    @GetMapping("/producto/{id}")
    public ResponseEntity<List<LineaPedido>> detailsProducto(@PathVariable int id) {
        List<LineaPedido> lineasPedido = lineaPedidoService.findByIdProducto(id);

        if (lineasPedido == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);  // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lineasPedido);
        }
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
        map.put("lineasPedido", lineaPedidoService.count());

        response = ResponseEntity
                .status(HttpStatus.OK)
                .body(map);

        return response;
    }
}