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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.balmis.proyecto.model.LineaPedido;
import com.balmis.proyecto.model.LineaPedidoId;
import com.balmis.proyecto.model.Usuario;
import com.balmis.proyecto.service.LineaPedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    @Operation(summary = "Obtener todas las lineas de pedido", description = "Retorna una lista con todas las lineas de pedido disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lineas de pedido obtenidas con éxito")
    })
    // ***************************************************************************
    @GetMapping("")
    public ResponseEntity<List<LineaPedido>> showLineasPedido() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lineaPedidoService.findAll());
    }

    // http://localhost:8080/proyecto/lineaspedido/2/2
    // ***************************************************************************
    // SWAGGER
    @Operation(summary = "Obtener linea de pedido por id_pedido y id_producto", description = "Retorna una linea de pedido específica basado en su id_pedido y id_producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Linea de pedido encontrada"),
            @ApiResponse(responseCode = "404", description = "Linea de pedido no encontrada", content = @Content())
    })
    // ***************************************************************************
    @GetMapping("/{id_pedido}/{id_producto}")
    public ResponseEntity<LineaPedido> detailsLineaPedido(@PathVariable int id_pedido, @PathVariable int id_producto) {
        LineaPedido lineasPedido = lineaPedidoService.findByIds(id_pedido, id_producto);

        if (lineasPedido == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null); // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lineasPedido);
        }
    }

    // http://localhost:8080/proyecto/lineaspedido/pedido/2
    // ***************************************************************************
    // SWAGGER
    @Operation(summary = "Obtener lineas de pedido de un pedido", description = "Retorna lineas de pedido con un id_pedido especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Linea de pedido encontrada"),
            @ApiResponse(responseCode = "404", description = "Linea de pedido no encontrada", content = @Content())
    })
    // ***************************************************************************
    @GetMapping("/pedido/{id}")
    public ResponseEntity<List<LineaPedido>> detailsPedido(@PathVariable int id) {
        List<LineaPedido> lineasPedido = lineaPedidoService.findByIdPedido(id);

        if (lineasPedido == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null); // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lineasPedido);
        }
    }


    // http://localhost:8080/proyecto/lineaspedido/producto/2
    // ***************************************************************************
    // SWAGGER
    @Operation(summary = "Obtener lineas de pedido de un producto", description = "Retorna lineas de pedido con un id_producto especifica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Linea de pedido encontrada"),
            @ApiResponse(responseCode = "404", description = "Linea de pedido no encontrada", content = @Content())
    })
    // ***************************************************************************
    @GetMapping("/producto/{id}")
    public ResponseEntity<List<LineaPedido>> detailsProducto(@PathVariable int id) {
        List<LineaPedido> lineasPedido = lineaPedidoService.findByIdProducto(id);

        if (lineasPedido == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null); // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(lineasPedido);
        }
    }

    // http://localhost:8080/proyecto/lineaspedido/pedido/user/1
    // ***************************************************************************
    // SWAGGER
    @Operation(summary = "Obtener usuario por id_pedido", description = "Retorna un usuario específica basado en id_pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content())
    })
    // ***************************************************************************
    @GetMapping("/pedido/user/{id_pedido}")
    public ResponseEntity<Usuario> detailsUser(@PathVariable int id_pedido) {
        Usuario usuario = lineaPedidoService.findUserByIdPedido(id_pedido);
        if (usuario == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null); // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(usuario);
        }
    }

    // http://localhost:8080/proyecto/lineaspedido/count
    // ***************************************************************************
    // SWAGGER
    @Operation(summary = "Obtener el número de lineas de pedido existentes", description = "Retorna la cantidad de lineas de pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Número de lineas de pedido obtenidas con éxito", content = @Content())
    })
    // ***************************************************************************
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> countLineasPedido() {

        ResponseEntity<Map<String, Object>> response = null;

        Map<String, Object> map = new HashMap<>();
        map.put("lineasPedido", lineaPedidoService.count());

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
    // http://localhost:8080/proyecto/lineaspedido
    // ***************************************************************************
    // SWAGGER
    @Operation(summary = "Crear una nueva linea de pedido", description = "Registra una nueva linea de pedido en el sistema con los datos proporcionados") // Doesn't work
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Linea de pedido creada con éxito", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content())
    })
    // ***************************************************************************
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createLineaPedido(
            @Valid @RequestBody LineaPedido lineaPedido) {
        ResponseEntity<Map<String, Object>> response;

        if (lineaPedido == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(map);
        } else {

            if (lineaPedido.getLineaPedidoId() == null
                    || lineaPedido.getCantidad() <= 0
                    || lineaPedido.getPrecio() == null) {

                Map<String, Object> map = new HashMap<>();
                map.put("error", "Los campos 'LineaPedidoId', 'cantidad' y 'precio' son obligatorios");

                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(map);
            } else {
                System.out.println(lineaPedido);
                LineaPedido lineaPedidoPost = lineaPedidoService.save(lineaPedido);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Linea de pedido creada con éxito");
                map.put("insertLineaPedido", lineaPedidoPost);

                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(map);
            }
        }
        return response;
    }

    // ****************************************************************************
    // UPDATE (PUT)
    // http://localhost:8080/proyecto/lineaspedido
    // ***************************************************************************
    // SWAGGER
    @Operation(summary = "Actualizar una linea de pedido existente", description = "Reemplaza completamente los datos de una linea de pedido identificada por su LineaPedidoId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Linea de pedido actualizada con éxito", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Linea de pedido no encontrada", content = @Content())
    })
    // ***************************************************************************
    @PutMapping("")
    public ResponseEntity<Map<String, Object>> updateLineaPedido(
            @Valid @RequestBody LineaPedido lineaPedidoUpdate) {

        ResponseEntity<Map<String, Object>> response;

        if (lineaPedidoUpdate == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } else {
            LineaPedidoId lineaPedidoId = lineaPedidoUpdate.getLineaPedidoId();
            LineaPedido existingLineaPedido = lineaPedidoService.findByIds(lineaPedidoUpdate.getLineaPedidoId().getPedido().getId(),
                    lineaPedidoUpdate.getLineaPedidoId().getProducto().getId());

            if (existingLineaPedido == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("error", "LineaPedido no encontrada");
                map.put("id", lineaPedidoId);

                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            } else {

                // Actualizar campos si están presentes
                if (lineaPedidoUpdate.getCantidad() != 0) {
                    existingLineaPedido.setCantidad(lineaPedidoUpdate.getCantidad());
                }
                if (lineaPedidoUpdate.getPrecio() != null) {
                    existingLineaPedido.setPrecio(lineaPedidoUpdate.getPrecio());
                }

                LineaPedido lineaPedidoPut = lineaPedidoService.save(existingLineaPedido);

                Map<String, Object> map = new HashMap<>();
                map.put("lineapedido", "LineaPedido actualizada con éxito");
                map.put("updatedLineaPedido", lineaPedidoPut);

                response = ResponseEntity.status(HttpStatus.OK).body(map);
            }
        }

        return response;
    }


    // ****************************************************************************
    // DELETE
    // http://localhost:8080/proyecto/lineaspedido/1/3
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Eliminar linea de pedido por id_pedido y id_producto",
            description = "Elimina una linea de pedido específica del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Linea de pedido eliminada con éxito", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Linea de pedido no encontrada", content = @Content())
    })
    // ***************************************************************************    
    @DeleteMapping("/{id_pedido}/{id_producto}")
    public ResponseEntity<Map<String, Object>> deleteLineaPedido(@PathVariable int id_pedido, @PathVariable int id_producto) {

        ResponseEntity<Map<String, Object>> response;

        LineaPedido existingLineaPedido = lineaPedidoService.findByIds(id_pedido, id_producto);
        if (existingLineaPedido == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "LineaPedido no encontrado");
            map.put("id_pedido", id_pedido);
            map.put("id_producto", id_producto);

            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        } else {

            lineaPedidoService.deleteById(existingLineaPedido.getLineaPedidoId());

            Map<String, Object> map = new HashMap<>();
            map.put("mensaje", "Linea de pedido eliminada con éxito");
            map.put("deletedLineaPedido", existingLineaPedido);

            response = ResponseEntity.status(HttpStatus.OK).body(map);
        }
        return response;
    }
}
