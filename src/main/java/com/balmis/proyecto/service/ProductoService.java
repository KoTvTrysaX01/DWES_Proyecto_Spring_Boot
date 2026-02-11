package com.balmis.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.balmis.proyecto.model.Producto;
import com.balmis.proyecto.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    public ProductoRepository productoRepository;


    // ************************
    // CONSULTAS
    // ************************ 
    @Transactional(readOnly = true)
    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto findById(int id){
        return productoRepository.findSqlByIdProducto(id);
    }

    @Transactional(readOnly = true)
    public List<Producto> findByIdCategoria(int id){
        return productoRepository.findSqlByIdCategoria(id);
    }

    @Transactional(readOnly = true) 
    public Long count() {
        return productoRepository.count();
    }

    @Transactional(readOnly = true) 
    public List<Producto> findByIdGrThan(int num) {
        return productoRepository.findSqlByIdGrThan(num);
    } 

    @Transactional(readOnly = true) 
    public Long countStock() {
        return productoRepository.countStockSql();
    } 


    // ************************
    // ACTUALIZACIONES
    // ************************ 
    @Transactional
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }


    @Transactional
    public Producto update(int id, Producto productoDetails) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    
        if (productoDetails.getNombre()  != null) {
            producto.setNombre(productoDetails.getNombre());
        }    
        if (productoDetails.getDescripcion()  != null) {
            producto.setDescripcion(productoDetails.getDescripcion());
        }
        if (productoDetails.getPrecio()  != null) {
            producto.setPrecio(productoDetails.getPrecio());
        }    
        producto.setStock(productoDetails.isStock());
        if (productoDetails.getImagen()  != null) {
            producto.setImagen(productoDetails.getImagen());
        }
        if (productoDetails.getCategoria()  != null) {
            producto.setCategoria(productoDetails.getCategoria());
        }
        
        return productoRepository.save(producto);
    }
    
    @Transactional
    public void deleteById(int id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productoRepository.deleteById(id);
    }
    
}
