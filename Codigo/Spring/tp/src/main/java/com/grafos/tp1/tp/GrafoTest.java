package com.grafos.tp1.tp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class GrafoTest {

  String nome = "C:\\PROGRAMMING\\Grafos\\Codigo\\Spring\\tp\\src\\main\\java\\com\\grafos\\tp1\\tp\\cidades.csv";

  @Test
  public void testaSeOGrafoEhCompleto() {
    Grafo grafo = Grafo.grafoCompleto(10);
    assertTrue(grafo.completo());
  }

  @Test
  public void deveAdicionarVertice() {
    Grafo grafo = new Grafo("");
    grafo.addVertice(1);
    assertNotNull(grafo.existeVertice(1));
  }

  @Test
  public void deveAdicionarAresta() {
    Grafo grafo = new Grafo("");
    grafo.addVertice(1);
    grafo.addVertice(2);
    grafo.addAresta(1, 2);
    assertNotNull(grafo.existeAresta(1, 2));
  }

  @Test
  public void testeArestaNaoDeveSerDirecionada() {
    Grafo grafo = new Grafo("");
    grafo.addVertice(1);
    grafo.addVertice(2);
    grafo.addAresta(1, 2);
    assertNotNull(grafo.existeAresta(2, 1));
  }

  @Test
  public void arestaNaoDeveExistir() {
    Grafo grafo = new Grafo("");
    grafo.addVertice(1);
    grafo.addVertice(2);
    grafo.addAresta(1, 2);
    assertNull(grafo.existeAresta(2, 3));
  }

  @Test
  public void testeQuandoNaoExisteVertice() {
    Grafo grafo = new Grafo("");
    grafo.addVertice(1);
    assertNull(grafo.existeVertice(2));
  }

  @Test
  public void naoDeveCriarDoisVerticesComIDsIguais() {
    Grafo grafo = new Grafo("");
    grafo.addVertice(1);
    assertFalse(grafo.addVertice(1));
  }

  @Test
  public void deveRetornarOrdemDoGrafo() {
    Grafo grafo = Grafo.grafoCompleto(4);
    assertEquals(4, grafo.ordem());
  }

  @Test
  public void deveRetornarTamanhoDoGrafo() {
    Grafo grafo = Grafo.grafoCompleto(5);
    assertEquals(15, grafo.tamanho());
  }

  @Test
  public void deveCarregarDeArquivo(){
    Grafo grafo = Grafo.grafoCompleto(5);
    grafo.salvar(nome);
    Grafo grafo2 = Grafo.grafoCompleto(5);
    grafo2.salvar(nome);
    assertEquals(grafo.completo(), grafo2.completo());
  }

  @Test
  public void criarSubgrafo() {
    Grafo grafo = new Grafo("");
    Lista<Integer> vertices = new Lista<>();
    for (int i = 0; i <= 10; i++) {
      grafo.addVertice(i);
      vertices.add(i);
    }
    for (int j = 0; j <= 10; j++) {
      grafo.addAresta(j, j + 1);
    }
    Grafo subgrafo = grafo.subGrafo(vertices);
    assertNotNull(subgrafo);
  }

  @Test
  public void criarSubgrafoComArestas() {
    Grafo grafo = new Grafo("");
    Lista<Integer> vertices = new Lista<>();
    for (int i = 0; i <= 10; i++) {
      grafo.addVertice(i);
      vertices.add(i);
    }
    for (int j = 0; j <= 10; j++) {
      grafo.addAresta(j, j + 1);
    }
    Grafo subgrafo = grafo.subGrafo(vertices);
    assertNotNull(subgrafo.existeAresta(1, 2));
  }

  @Test
  public void pesquisaEmProfundidade() {
    Grafo grafo = new Grafo("");
    Lista<Integer> vertices = new Lista<>();
    for (int i = 0; i <= 10; i++) {
      grafo.addVertice(i);
      vertices.add(i);
    }
    for (int j = 0; j <= 10; j++) {
      grafo.addAresta(j, j + 1);
    }
    Grafo subgrafo = grafo.subGrafo(vertices);
    assertNotNull(subgrafo.existeAresta(1, 2));
  }
  @Test
  public void pesquisaEmLargura() {
    Grafo grafo = new Grafo("");
    Lista<Integer> vertices = new Lista<>();
    for (int i = 0; i <= 10; i++) {
      grafo.addVertice(i);
      vertices.add(i);
    }
    for (int j = 0; j <= 10; j++) {
      grafo.addAresta(j, j + 1);
    }
    Grafo subgrafo = grafo.subGrafo(vertices);
    assertNotNull(subgrafo.existeAresta(1, 2));
  }

  @Test
  public void retornaGrauDeUmVertice() {
    Grafo grafo = Grafo.grafoCompleto(3);
    Vertice vertice = grafo.existeVertice(0);
    assertEquals(2, vertice.grau());
  }

  @Test
  public void retornaQuantidadeDeVizinhos() {
    HashMap<Integer, List<Integer>> vizinhos = new HashMap<>();
    List<Integer> listaVizinhos = new ArrayList<>();
    Grafo grafo = Grafo.grafoCompleto(4);

    for(int i = 0; i < grafo.ordem(); i++) {
      for(int j = 0; j < grafo.ordem(); j++) {
        if(grafo.existeAresta(i, j) != null) {
          listaVizinhos.add(j);
        }
      }
      vizinhos.put(i, listaVizinhos);
      listaVizinhos = new ArrayList<>();
    }

    assertEquals(3, vizinhos.get(0).size());
  }

  @Test
  public void retornaVizinhosDosVertices() {
    HashMap<Integer, List<Integer>> vizinhos = new HashMap<>();
    List<Integer> listaVizinhos = new ArrayList<>();
    Grafo grafo = Grafo.grafoCompleto(4);

    for(int i = 0; i < grafo.ordem(); i++) {
      for(int j = 0; j < grafo.ordem(); j++) {
        if(grafo.existeAresta(i, j) != null) {
          listaVizinhos.add(j);
        }
      }
      vizinhos.put(i, listaVizinhos);
      listaVizinhos = new ArrayList<>();
    }

    int vertice1 = vizinhos.get(0).get(0);
    int vertice2 = vizinhos.get(0).get(1);
    int vertice3 = vizinhos.get(0).get(2);

    assertEquals(1, vertice1);
    assertEquals(2, vertice2);
    assertEquals(3, vertice3);
  }
}
