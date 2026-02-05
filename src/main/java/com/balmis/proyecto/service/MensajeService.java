package com.balmis.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.balmis.proyecto.model.Mensaje;
import com.balmis.proyecto.repository.MensajeRepository;

public class MensajeService {
    @Autowired
    public MensajeRepository mensajeRepository;


    // ************************
    // CONSULTAS
    // ************************ 
    @Transactional(readOnly = true)
    public List<Mensaje> findAll(){
        return mensajeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mensaje findById(int id){
        return mensajeRepository.findSqlByIdMensaje(id);
    }

    @Transactional(readOnly = true)
    public Mensaje findByTitulo(String titulo){
        return mensajeRepository.findSqlByTitulo(titulo);
    }

    @Transactional(readOnly = true) 
    public Long count() {
        return mensajeRepository.count();
    }

    @Transactional(readOnly = true) 
    public List<Mensaje> findByIdGrThan(int num) {
        return mensajeRepository.findSqlByIdGrThan(num);
    } 

    // ************************
    // ACTUALIZACIONES
    // ************************ 
    @Transactional
    public Mensaje save(Mensaje mensaje) {
        return mensajeRepository.save(mensaje);
    }


    @Transactional
    public Mensaje update(int id, Mensaje mensajeDetails) {
        Mensaje mensaje = mensajeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        
        if (mensajeDetails.getMensaje() != null) {
            mensaje.setMensaje(mensajeDetails.getMensaje());
        }
        if (mensajeDetails.getTitulo()  != null) {
            mensaje.setTitulo(mensajeDetails.getTitulo());
        }
        if (mensajeDetails.getEmail()  != null) {
            mensaje.setEmail(mensajeDetails.getEmail());
        }
        if (mensajeDetails.getPost_date()  != null) {
            mensaje.setPost_date(mensajeDetails.getPost_date());
        }
        
        return mensajeRepository.save(mensaje);
    }
    
    @Transactional
    public void deleteById(int id) {
        if (!mensajeRepository.existsById(id)) {
            throw new RuntimeException("Mensaje no encontrado");
        }
        mensajeRepository.deleteById(id);
    }
}
