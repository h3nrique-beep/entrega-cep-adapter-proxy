package com.example.produtos;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Proxy: valida o formato, cacheia e loga; delega ao provedor real. */
@Component
@Primary
public class CepProxy implements ConsultaCep {
    private final ConsultaCep provedor;
    private final Map<String, Endereco> cache = new ConcurrentHashMap<>();
    private final AppLogger log = AppLogger.getInstance();

    public CepProxy(@Qualifier("provedorCep") ConsultaCep provedor) { this.provedor = provedor; }

    @Override
    public Endereco buscar(String cep) {
        if (cep == null || !cep.matches("\d{8}")) {
            log.erro("CEP inválido bloqueado: " + cep);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP deve ter 8 dígitos");
        }
        Endereco e = cache.get(cep);
        if (e != null) {
            log.info("CEP " + cep + " veio do CACHE");
            return e;
        }
        e = provedor.buscar(cep);
        cache.put(cep, e);
        log.info("CEP " + cep + " veio do PROVEDOR " + provedor.getClass().getSimpleName());
        return e;
    }
}
