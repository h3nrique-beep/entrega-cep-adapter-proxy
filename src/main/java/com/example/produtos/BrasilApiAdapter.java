package com.example.produtos;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/** Adapter: JSON da BrasilAPI (street/neighborhood/city/state) -> Endereco. */
@Component("provedorCep")
@ConditionalOnProperty(name = "cep.provedor", havingValue = "brasilapi")
public class BrasilApiAdapter implements ConsultaCep {
    private final RestTemplate http = new RestTemplate();

    @Override
    @SuppressWarnings("unchecked")
    public Endereco buscar(String cep) {
        try {
            Map<String, Object> j = http.getForObject("https://brasilapi.com.br/api/cep/v1/{cep}", Map.class, cep);
            return new Endereco(cep, (String) j.get("street"), (String) j.get("neighborhood"),
                    (String) j.get("city"), (String) j.get("state"));
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP não encontrado");
        }
    }
}
