package com.example.produtos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService service;
    private final AppLogger log = AppLogger.getInstance();

    public ProdutoController(ProdutoService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto p) {
        log.debug("POST /produtos");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(p));
    }

    @GetMapping
    public List<Produto> listar() {
        log.debug("GET /produtos");
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscar(@PathVariable Long id) {
        log.debug("GET /produtos/" + id);
        return service.buscar(id).map(ResponseEntity::ok).orElseGet(() -> notFound(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto p) {
        log.debug("PUT /produtos/" + id);
        return service.atualizar(id, p).map(ResponseEntity::ok).orElseGet(() -> notFound(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        log.debug("DELETE /produtos/" + id);
        if (service.remover(id)) return ResponseEntity.noContent().build();
        return notFound(id);
    }

    private <T> ResponseEntity<T> notFound(Long id) {
        log.erro("404 produto id=" + id);
        return ResponseEntity.notFound().build();
    }
}
