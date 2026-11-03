package com.example.produtos;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Map;

/** Adapter: JSON da ViaCEP (logradouro/localidade) -> Endereco. */
@Component("provedorCep")
@ConditionalOnProperty(name = "cep.provedor", havingValue = "viacep", matchIfMissing = true)
public class ViaCepAdapter implements ConsultaCep {
    private final RestTemplate http = new RestTemplate();

    @Override
    @SuppressWarnings("unchecked")
    public Endereco buscar(String cep) {
        Map<String, Object> j = http.getForObject("https://viacep.com.br/ws/{cep}/json/", Map.class, cep);
        if (j == null || j.containsKey("erro")) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP não encontrado");
        return new Endereco(cep, (String) j.get("logradouro"), (String) j.get("bairro"),
                (String) j.get("localidade"), (String) j.get("uf"));
    }
}
