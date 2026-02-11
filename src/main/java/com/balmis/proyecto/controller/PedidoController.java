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

import com.balmis.proyecto.model.Pedido;
import com.balmis.proyecto.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Pedidos", description = "API para gestión de pedidos")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    

    @Autowired
    private PedidoService pedidoService;

    // ***************************************************************************
    // CONSULTAS
    // ***************************************************************************
    // http://localhost:8080/proyecto/pedidos
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener todos los pedidos",
            description = "Retorna una lista con todos los pedidos disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "pedidos obtenidos con éxito")
    })
    // ***************************************************************************    
    @GetMapping("")
    public ResponseEntity<List<Pedido>> showPedidos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoService.findAll());
    }

    // http://localhost:8080/proyecto/pedidos/2
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener Pedido por ID",
            description = "Retorna un Pedido especifico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content())
    })
    // ***************************************************************************    
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> detailsPedido(@PathVariable int id) {
        Pedido Pedido = pedidoService.findById(id);

        if (Pedido == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);  // 404 Not Found
        } else {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Pedido);
        }
    }

    // http://localhost:8080/proyecto/pedidos/mayor/7
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener pedidos mayores de un ID",
            description = "Retorna una lista con todos los pedidos con ID mayor que un valor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "pedidos obtenidos con éxito")
    })
    // ***************************************************************************    
    @GetMapping("/mayor/{id}")
    public ResponseEntity<List<Pedido>> showPedidosMayores(@PathVariable int id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoService.findByIdGrThan(id));
    }

    // http://localhost:8080/proyecto/pedidos/user/7
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener pedidos de un usuario",
            description = "Retorna una lista con todos los pedidos de un Usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "pedidos obtenidos con éxito")
    })
    // ***************************************************************************    
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Pedido>> showPedidos(@PathVariable int user_id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoService.findByUserId(user_id));
    }


    // http://localhost:8080/proyecto/pedidos/count
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Obtener el número de pedidos existentes",
            description = "Retorna la cantidad de pedidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Número de pedidos obtenidos con éxito", content = @Content())
    })
    // ***************************************************************************    
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> countPedidos() {

        ResponseEntity<Map<String, Object>> response = null;

        Map<String, Object> map = new HashMap<>();
        map.put("pedidos", pedidoService.count());

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
    // http://localhost:8080/proyecto/pedidos
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Crear una nueva pedido",
            description = "Registra una nueva pedido en el sistema con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido creada con éxito", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content())
    })
    // ***************************************************************************
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> createPedido(
            @Valid @RequestBody Pedido pedido) {

        ResponseEntity<Map<String, Object>> response;

        if (pedido == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(map);
        } else {

            if (pedido.getPedido() == null || pedido.getPedido().trim().isEmpty()
                    || pedido.getPrecio_total() == null
                    || pedido.getTel() == null || pedido.getTel().trim().isEmpty()
                    || pedido.getDireccion() == null || pedido.getDireccion().trim().isEmpty()
                    || pedido.getPedido_date() == null
                    || pedido.getUsuario() == null
                    ) {

                Map<String, Object> map = new HashMap<>();
                map.put("error", "Los campos 'pedido', 'precio', 'tel', 'direccion', 'pedido_date' y 'usuario' son obligatorios");

                response = ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(map);
            } else {
                System.out.println(pedido);
                Pedido PedidoPost = pedidoService.save(pedido);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Pedido creada con éxito");
                map.put("insertPedido", PedidoPost);

                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(map);
            }
        }

        return response;
    }

        // ****************************************************************************
    // UPDATE (PUT)
    // http://localhost:8080/proyecto/pedidos
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Actualizar un Pedido existente",
            description = "Reemplaza completamente los datos de unu Pedido identificado por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido actualizado con éxito", content = @Content()),
        @ApiResponse(responseCode = "400", description = "Datos de actualización inválidos", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content())
    })
    // ***************************************************************************    
    @PutMapping("")
    public ResponseEntity<Map<String, Object>> updatePedido(
            @Valid @RequestBody Pedido pedidoUpdate) {

        ResponseEntity<Map<String, Object>> response;

        if (pedidoUpdate == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "El cuerpo de la solicitud no puede estar vacío");

            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
        } else {
            int id = pedidoUpdate.getId();
            Pedido existingPedido = pedidoService.findById(id);

            if (existingPedido == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("error", "Pedido no encontrado");
                map.put("id", id);

                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
            } else {

                // Actualizar campos si están presentes
                if (pedidoUpdate.getPedido() != null) {
                    existingPedido.setPedido(pedidoUpdate.getPedido());
                }
                if (pedidoUpdate.getPrecio_total() != null) {
                    existingPedido.setPrecio_total(pedidoUpdate.getPrecio_total());
                }
                if (pedidoUpdate.getTel() != null) {
                    existingPedido.setTel(pedidoUpdate.getTel());
                }
                if (pedidoUpdate.getDireccion() != null) {
                    existingPedido.setDireccion(pedidoUpdate.getDireccion());
                }
                if (pedidoUpdate.getPedido_date() != null) {
                    existingPedido.setPedido_date(pedidoUpdate.getPedido_date());
                }
                if (pedidoUpdate.getUsuario() != null) {
                    existingPedido.setUsuario(pedidoUpdate.getUsuario());
                }              

                Pedido pedidoPut = pedidoService.save(existingPedido);

                Map<String, Object> map = new HashMap<>();
                map.put("mensaje", "Pedido actualizada con éxito");
                map.put("updatedPedido", pedidoPut);

                response = ResponseEntity.status(HttpStatus.OK).body(map);
            }
        }

        return response;
    }


    // ****************************************************************************
    // DELETE
    // http://localhost:8080/proyecto/pedidos/16
    // ***************************************************************************    
    // SWAGGER
    @Operation(summary = "Eliminar pedido por ID",
            description = "Elimina un pedido especifico del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido eliminado con éxito", content = @Content()),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content())
    })
    // ***************************************************************************    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePedido(@PathVariable int id) {

        ResponseEntity<Map<String, Object>> response;

        Pedido existingPedido = pedidoService.findById(id);
        if (existingPedido == null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "Pedido no encontrado");
            map.put("id", id);

            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
        } else {

            pedidoService.deleteById(id);

            Map<String, Object> map = new HashMap<>();
            map.put("mensaje", "Pedido eliminado con éxito");
            map.put("deletedPedido", existingPedido);

            response = ResponseEntity.status(HttpStatus.OK).body(map);
        }
        return response;
    }
}



