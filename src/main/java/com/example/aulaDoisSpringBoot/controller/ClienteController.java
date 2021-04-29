/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.aulaDoisSpringBoot.controller;

import com.example.aulaDoisSpringBoot.model.Cliente;
import com.example.aulaDoisSpringBoot.repository.ClienteRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Wilson Wolf Costa <wilson.w.costa@gmail.com>
 */
@Controller
@RequestMapping("/clientes")
public class ClienteController {
    
    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    @RequestMapping("")
    public ModelAndView page(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        return new ModelAndView("clientes/list", "clientes", clientes);
    }
    
}
