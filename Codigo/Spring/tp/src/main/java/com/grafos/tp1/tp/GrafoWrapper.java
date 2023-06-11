package com.grafos.tp1.tp;

import java.util.List;

public class GrafoWrapper {
    GrafoWrapper(List<VerticeWrapper> vertices, List<ArestaWrapper> arestas){
        this.vertices = vertices;
        this.arestas = arestas;
    }

    public List<VerticeWrapper> vertices;
    public List<ArestaWrapper> arestas;
}
