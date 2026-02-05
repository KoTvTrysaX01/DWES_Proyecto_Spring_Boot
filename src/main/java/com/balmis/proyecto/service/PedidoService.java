package com.balmis.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balmis.proyecto.repository.PedidoRepository;

@Service
public class PedidoService {
    

    @Autowired
    public PedidoRepository pedidoRepository;
}
