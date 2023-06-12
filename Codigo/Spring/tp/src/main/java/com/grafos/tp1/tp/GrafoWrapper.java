package com.grafos.tp1.tp;

import java.util.List;

public class GrafoWrapper {
    GrafoWrapper(List<VerticeWrapper> vertices, List<ArestaWrapper> arestas, Double somaArvoreGeradoraMinima){
        this.vertices = vertices;
        this.arestas = arestas;
        this.somaArvoreGeradoraMinima = somaArvoreGeradoraMinima;
    }

    public List<VerticeWrapper> vertices;
    public List<ArestaWrapper> arestas;
    public Double somaArvoreGeradoraMinima;
}
