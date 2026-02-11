package com.balmis.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.balmis.proyecto.model.LineaPedido;
import com.balmis.proyecto.model.LineaPedidoId;
import com.balmis.proyecto.model.Usuario;
import com.balmis.proyecto.repository.LineaPedidoRepository;

@Service
public class LineaPedidoService {

    @Autowired
    public LineaPedidoRepository lineaPedidoRepository;

    // ************************
    // CONSULTAS
    // ************************ 

    @Transactional(readOnly = true)
    public LineaPedido findByIds(int id_pedido, int id_producto){
        return lineaPedidoRepository.findSqlByIds(id_pedido, id_producto);
    }

    @Transactional(readOnly = true)
    public List<LineaPedido> findAll(){
        return lineaPedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<LineaPedido> findByIdPedido(int id_pedido){
        return lineaPedidoRepository.findSqlByIdPedido(id_pedido);
    }

    @Transactional(readOnly = true)
    public List<LineaPedido> findByIdProducto(int id_producto){
        return lineaPedidoRepository.findSqlByIdProducto(id_producto);
    }

    @Transactional(readOnly = true) 
    public Long count() {
        return lineaPedidoRepository.count();
    }

    @Transactional(readOnly = true) 
    public Usuario findUserByIdPedido(int id_pedido) {
        return lineaPedidoRepository.findSqlUserByIdPedido(id_pedido);
    }

    // ************************
    // ACTUALIZACIONES
    // ************************ 
    @Transactional
    public LineaPedido save(LineaPedido lineaPedido) {
        return lineaPedidoRepository.save(lineaPedido);
    }


    @Transactional
    public LineaPedido update(LineaPedidoId lineaPedidoId, LineaPedido lineaPedidoDetails) {
        LineaPedido lineaPedido = lineaPedidoRepository.findById(lineaPedidoId)
            .orElseThrow(() -> new RuntimeException("LineaPedido no encontrado"));
        
        if (lineaPedidoDetails.getLineaPedidoId().getPedido() != null) {
            lineaPedido.getLineaPedidoId().setPedido(lineaPedidoDetails.getLineaPedidoId().getPedido());        // lineaPedidoId == null?
        }
        if (lineaPedido.getLineaPedidoId().getProducto() != null) {
            lineaPedido.getLineaPedidoId().setProducto(lineaPedidoDetails.getLineaPedidoId().getProducto());    // lineaPedidoId == null?
        }
        if (lineaPedidoDetails.getCantidad() != 0) {
            lineaPedido.setCantidad(lineaPedidoDetails.getCantidad());
        }
        if (lineaPedidoDetails.getPrecio()  != null) {
            lineaPedido.setPrecio(lineaPedidoDetails.getPrecio());
        }
        return lineaPedidoRepository.save(lineaPedido);
    }

    
    @Transactional
    public void deleteById(LineaPedidoId lineaPedidoId) {
        if (!lineaPedidoRepository.existsById(lineaPedidoId)) {
            throw new RuntimeException("LineaPedido no encontrado");
        }
        lineaPedidoRepository.deleteById(lineaPedidoId);
    }
    
}
