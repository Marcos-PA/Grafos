package com.grafos.tp1.tp;

import java.util.List;

public class VerticeWrapper {
    public VerticeWrapper(Cidade cidade, List<ArestaWrapper> arestas, List<Cidade> vizinhos, Integer grau) {
        this.cidade = cidade;
        this.arestas = arestas;
        this.vizinhos = vizinhos;
        this.grau = grau;
    }

    public Cidade cidade;
    public List<ArestaWrapper> arestas;
    public List<Cidade> vizinhos;
    public Integer grau;
}
