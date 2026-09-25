package com.example.buscacep.controller;

import com.example.buscacep.model.EnderecoResponse;
import com.example.buscacep.service.CepService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CepController {

    private final CepService cepService;

    public CepController(CepService cepService) {
        this.cepService = cepService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("resultado", null);
        return "index";
    }

    @PostMapping("/consultar")
    public String consultar(@RequestParam String cep, Model model) {
        try {
            EnderecoResponse resultado = cepService.consultar(cep);
            model.addAttribute("resultado", resultado);
            model.addAttribute("mensagemErro", null);
        } catch (Exception e) {
            model.addAttribute("resultado", null);
            model.addAttribute("mensagemErro", e.getMessage());
        }
        return "index";
    }
}
