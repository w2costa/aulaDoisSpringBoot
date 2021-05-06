/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.aulaDoisSpringBoot.controller;

import com.example.aulaDoisSpringBoot.model.Cliente;
import com.example.aulaDoisSpringBoot.repository.ClienteRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Wilson Wolf Costa <wilson.w.costa@gmail.com>
 */
@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final String CLIENTE_URI = "clientes/";

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @RequestMapping("")
    public ModelAndView page(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        return new ModelAndView(CLIENTE_URI + "list", "clientes", clientes);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Cliente cliente) {
        return new ModelAndView(CLIENTE_URI + "view", "cliente", cliente);
    }

    @RequestMapping("/novo")
    public String novo(@ModelAttribute Cliente cliente) {
        return CLIENTE_URI + "form";
    }

    @PostMapping(params = "form")
    public ModelAndView salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView(CLIENTE_URI + "form", "formErrors", result.getAllErrors());
        }
        cliente = clienteRepository.saveAndFlush(cliente);
        redirect.addFlashAttribute("globalMessage", "Cliente gravado com sucesso.");
        return new ModelAndView("redirect:/" + CLIENTE_URI + "{cliente.id}", "cliente.id", cliente.getId());
    }

    @GetMapping("remover/{id}")
    public ModelAndView remover(@PathVariable("id") int id, RedirectAttributes redirect) {
        clienteRepository.deleteById(id);
        Iterable<Cliente> clientes = clienteRepository.findAll();

        ModelAndView mv = new ModelAndView(CLIENTE_URI + "list", "clientes", clientes);
        mv.addObject("globalMessage", "Cliente removido com sucesso.");

        return mv;
    }

    @GetMapping("alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") Cliente cliente) {
        return new ModelAndView(CLIENTE_URI + "form", "cliente", cliente);
    }
}
