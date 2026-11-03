package com.example.produtos;

import jakarta.persistence.*;

@Entity
public class Produto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double preco;

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}
