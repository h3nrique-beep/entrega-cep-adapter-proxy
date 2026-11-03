package com.example.produtos;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    private final ProdutoRepository repo;
    private final AppLogger log = AppLogger.getInstance();

    public ProdutoService(ProdutoRepository repo) { this.repo = repo; }

    public Produto criar(Produto p) {
        log.debug("criar produto nome=" + p.getNome() + " preco=" + p.getPreco());
        Produto salvo = repo.save(p);
        log.info("produto criado id=" + salvo.getId());
        return salvo;
    }

    public List<Produto> listar() {
        List<Produto> todos = repo.findAll();
        log.info("listados " + todos.size() + " produtos");
        return todos;
    }

    public Optional<Produto> buscar(Long id) {
        log.debug("buscar produto id=" + id);
        Optional<Produto> p = repo.findById(id);
        if (p.isPresent()) log.info("produto encontrado id=" + id);
        else log.erro("produto não encontrado id=" + id);
        return p;
    }

    public Optional<Produto> atualizar(Long id, Produto novo) {
        log.debug("atualizar produto id=" + id + " nome=" + novo.getNome() + " preco=" + novo.getPreco());
        Optional<Produto> p = repo.findById(id).map(atual -> {
            atual.setNome(novo.getNome());
            atual.setPreco(novo.getPreco());
            return repo.save(atual);
        });
        if (p.isPresent()) log.info("produto atualizado id=" + id);
        else log.erro("produto não encontrado para atualizar id=" + id);
        return p;
    }

    public boolean remover(Long id) {
        log.debug("remover produto id=" + id);
        if (!repo.existsById(id)) {
            log.erro("produto não encontrado para remover id=" + id);
            return false;
        }
        repo.deleteById(id);
        log.info("produto removido id=" + id);
        return true;
    }
}
