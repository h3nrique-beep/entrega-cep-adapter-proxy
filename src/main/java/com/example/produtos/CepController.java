package com.example.produtos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CepController {
    private final ConsultaCep consulta;

    public CepController(ConsultaCep consulta) { this.consulta = consulta; }

    @GetMapping("/cep/{cep}")
    public Endereco buscar(@PathVariable String cep) { return consulta.buscar(cep); }
}
