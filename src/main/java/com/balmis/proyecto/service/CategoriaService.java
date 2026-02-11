package com.balmis.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.balmis.proyecto.model.Categoria;
import com.balmis.proyecto.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    public CategoriaRepository categoriaRepository;


    // ************************
    // CONSULTAS
    // ************************ 
    @Transactional(readOnly = true)
    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria findById(int id){
        return categoriaRepository.findSqlByIdCategory(id);
    }

    @Transactional(readOnly = true) 
    public Long count() {
        return categoriaRepository.count();
    }

    @Transactional(readOnly = true) 
    public List<Categoria> findByIdGrThan(int num) {
        return categoriaRepository.findSqlByIdGrThan(num);
    } 

    // ************************
    // ACTUALIZACIONES
    // ************************ 
    @Transactional
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }


    @Transactional
    public Categoria update(int id, Categoria categoriaDetails) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        
        if (categoriaDetails.getCategoria() != null) {
            categoria.setCategoria(categoriaDetails.getCategoria());
        }
        if (categoriaDetails.getDescripcion()  != null) {
            categoria.setDescripcion(categoriaDetails.getDescripcion());
        }
        if (categoriaDetails.getImagen()  != null) {
            categoria.setImagen(categoriaDetails.getImagen());
        }
        
        return categoriaRepository.save(categoria);
    }
    
    @Transactional
    public void deleteById(int id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria no encontrada");
        }
        categoriaRepository.deleteById(id);
    }
}