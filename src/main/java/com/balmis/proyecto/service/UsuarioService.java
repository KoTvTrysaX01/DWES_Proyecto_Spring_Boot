package com.balmis.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.balmis.proyecto.model.Usuario;
import com.balmis.proyecto.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    public UsuarioRepository userRepository;
    
    // ************************
    // CONSULTAS
    // ************************  
    @Transactional(readOnly = true) 
    public List<Usuario> findAll() {
        return userRepository.findSqlAll();
    }
    
    @Transactional(readOnly = true) 
    public Usuario findById(int userId) {
        return userRepository.findSqlById(userId);
    }

    @Transactional(readOnly = true) 
    public Long count() {
        return userRepository.count();
    }    
    
    @Transactional(readOnly = true) 
    public List<Usuario> findByIdGrThan(int userId) {
        return userRepository.findSqlByIdGrThan(userId);
    }
    
    // ************************
    // ACTUALIZACIONES
    // ************************  

    @Transactional
    public Usuario save(Usuario user) {
        return userRepository.save(user);
    }
    
    @Transactional
    public Usuario update(int id, Usuario userUpdate) {
        Usuario usuario = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (userUpdate.getUsername() != null) {
            usuario.setUsername(userUpdate.getUsername());
        }
        if (userUpdate.getEmail() != null) {
            usuario.setEmail(userUpdate.getEmail());
        }
        if (userUpdate.getPassword() != null) {
            usuario.setPassword(userUpdate.getPassword());
        }
        usuario.setAdministrador(userUpdate.isAdministrador());
        usuario.setUsuario(userUpdate.isUsuario());
        usuario.setInvitado(userUpdate.isInvitado());
        usuario.setActivado(userUpdate.isActivado());
        
        
        return userRepository.save(usuario);
    }
    
    @Transactional
    public void deleteById(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    } 
}
