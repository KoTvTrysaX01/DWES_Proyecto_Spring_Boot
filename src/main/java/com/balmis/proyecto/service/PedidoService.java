package com.balmis.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.balmis.proyecto.model.Pedido;
import com.balmis.proyecto.repository.PedidoRepository;

@Service
public class PedidoService {
    

    @Autowired
    public PedidoRepository pedidoRepository;

    // ************************
    // CONSULTAS
    // ************************ 
    @Transactional(readOnly = true)
    public List<Pedido> findAll(){
        return pedidoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pedido findById(int id){
        return pedidoRepository.findSqlByIdPedido(id);
    }

    @Transactional(readOnly = true)
    public List<Pedido> findByUserId(int id){
        return pedidoRepository.findSqlByIdUsuario(id);
    }

    @Transactional(readOnly = true) 
    public Long count() {
        return pedidoRepository.count();
    }

    @Transactional(readOnly = true) 
    public List<Pedido> findByIdGrThan(int num) {
        return pedidoRepository.findSqlByIdGrThan(num);
    }
    
    // ************************
    // ACTUALIZACIONES
    // ************************ 
    @Transactional
    public Pedido save(Pedido categoria) {
        return pedidoRepository.save(categoria);
    }


    @Transactional
    public Pedido update(int id, Pedido pedidoDetails) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        if (pedidoDetails.getPedido() != null) {
            pedido.setPedido(pedidoDetails.getPedido());
        }
        if (pedidoDetails.getPrecio_total()  != null) {
            pedido.setPrecio_total(pedidoDetails.getPrecio_total());
        }
        if (pedidoDetails.getTel()  != null) {
            pedido.setTel(pedidoDetails.getTel());
        }
        if (pedidoDetails.getDireccion()  != null) {
            pedido.setDireccion(pedidoDetails.getDireccion());
        }
        if (pedidoDetails.getPedido_date()  != null) {
            pedido.setPedido_date(pedidoDetails.getPedido_date());
        }
        if (pedidoDetails.getUsuario()  != null) {
            pedido.setUsuario(pedidoDetails.getUsuario());
        }
        return pedidoRepository.save(pedido);
    }
    
    @Transactional
    public void deleteById(int id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado");
        }
        pedidoRepository.deleteById(id);
    }
}
