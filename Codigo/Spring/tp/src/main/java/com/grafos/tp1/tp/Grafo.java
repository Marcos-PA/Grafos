package com.grafos.tp1.tp;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** 
 * MIT License
 *
 * Copyright(c) 2021-23 João Caram <caram@pucminas.br>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * Classe básica para um Grafo simples não direcionado.
 */
public class Grafo {
    public final String nome;
    private ABB<Vertice> vertices;

    /**
     * Retorna um grafo completo de ordem n, com todos os vértices conectados entre
     * si
     * 
     * @param ordem
     * @return Grafo
     */
    public static Grafo grafoCompleto(int ordem) {
        Grafo grafo = new Grafo("graph");

        for (int i = 0; i < ordem; i++) {
            grafo.addVertice(i);
        }

        for (int i = 0; i < ordem; i++) {
            for (int j = i + 1; j < ordem; j++) {
                grafo.addAresta(i, j);
            }
        }
        return grafo;
    }

    /**
     * Construtor. Cria um grafo vazio com um nome escolhido pelo usuário. Em caso
     * de nome não informado
     * (string vazia), recebe o nome genérico "Grafo"
     */
    public Grafo(String nome) {
        if (nome.length() == 0)
            this.nome = "Grafo";
        else
            this.nome = nome;
        this.vertices = new ABB<>();
    }

    /**
     * Retorna o nome do grafo (string), caso seja necessário em outras
     * classes/sistemas
     * 
     * @return O nome do grafo (uma string)
     */
    public String nome() {
        return this.nome;
    }

    /**
     * Metodo responsavel por salvar os dados do grafo em um arquivo de texto.
     * 
     * @param nomeArquivo
     * @return void
     * @throws IOException
     */
    public void salvar(String nomeArquivo) {
        StringBuilder verticesGrafo = new StringBuilder();
        StringBuilder textoCaminhos = new StringBuilder();
        FileWriter arquivo;

        try {
            arquivo = new FileWriter(nomeArquivo);
            for (Vertice vertice : this.vertices.allElements(new Vertice[this.vertices.size()])) {
                verticesGrafo.append(vertice.getId() + ";");
                if (vertice.getArestas().size() > 0) {
                    for (Aresta arestaVertice : vertice.getArestas()
                            .allElements(new Aresta[vertice.getArestas().size()]))
                        textoCaminhos.append(String.format("%o;%o;%s\n", vertice.getId(), arestaVertice.destino(),
                                ("" + arestaVertice.peso())));
                }
            }
            textoCaminhos.deleteCharAt(textoCaminhos.length() - 1);
            arquivo.write(verticesGrafo.toString() + "\n");
            arquivo.write(textoCaminhos.toString());
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adiciona um vértice com o id especificado. Ignora a ação e retorna false se
     * já existir
     * um vértice com este id
     * 
     * @param id O identificador do vértice a ser criado/adicionado
     * @return TRUE se houve a inclusão do vértice, FALSE se já existia vértice com
     *         este id
     */
    public boolean addVertice(int id) {
        Vertice novo = new Vertice(id);
        return this.vertices.add(id, novo);
    }

    /**
     * Remove um vértice com o id especificado. Retorna nulo caso o vértice não
     * exista/não possa ser removido.
     * 
     * @param id O identificador do vértice a ser removido
     * @return Vertice (removido) / null (não removido)
     */
    public Vertice removeVertice(int id) {
        Vertice vertice = this.vertices.find(id);
        if (vertice != null) {
            for (Vertice vertice2 : this.vertices.allElements(new Vertice[this.vertices.size()])) {
                if (vertice2.getArestas().find(id) != null)
                    vertice2.getArestas().remove(id);
            }
            this.vertices.remove(id);
            return vertice;
        } else
            return null;
    }

    /**
     * Retorna o vértice com o id especificado. Retorna nulo caso o vértice não
     * exista.
     * 
     * @param id O identificador do vértice a ser conferido.
     * @return Vertice (encontrado) / null (não encontrado)
     */

    public Vertice existeVertice(int idVertice) {
        Vertice vertice = this.vertices.find(idVertice);
        if (vertice != null)
            return vertice;
        else
            return null;
    }

    /**
     * Adiciona uma aresta entre dois vértices do grafo, caso os dois vértices
     * existam no grafo.
     * Caso a aresta já exista, ou algum dos vértices não existir, o comando é
     * ignorado e retorna FALSE.
     * 
     * @param origem  Vértice de origem
     * @param destino Vértice de destino
     * @param peso    Peso da aresta
     * @return TRUE se foi inserida, FALSE caso contrário
     */
    public boolean addAresta(int origem, int destino, double peso) {
        return adicionarAresta(origem, destino, peso);
    }

    private boolean adicionarAresta(int origem, int destino, double peso) {
        boolean adicionou = false;
        Vertice saida = this.existeVertice(origem);
        Vertice chegada = this.existeVertice(destino);
        if (saida != null && chegada != null) {
            adicionou = (saida.addAresta(destino, peso) && chegada.addAresta(origem, peso));
        }
        return adicionou;
    }

    public boolean addAresta(int origem, int destino) {
        return adicionarAresta(origem, destino, -1);
    }

    /**
     * Remove uma aresta entre dois vértices do grafo, caso os dois vértices existam
     * 
     * @param origem
     * @param destino
     * @return Aresta
     * @return null
     */
    public Aresta removeAresta(int origem, int destino) {
        for (Vertice vertice : this.vertices.allElements(new Vertice[this.vertices.size()])) {
            if (vertice.getId() == origem) {
                return vertice.removeAresta(destino);
            }
        }

        return null;
    }

    /**
     * Verifica se existe uma aresta entre dois vértices do grafo
     * 
     * @param verticeA
     * @param verticeB
     * @return Aresta
     * @return null
     */
    public Aresta existeAresta(int verticeA, int verticeB) {
        for (Vertice vertice : this.vertices.allElements(new Vertice[this.vertices.size()])) {
            if (vertice.getId() == verticeA) {
                return vertice.existeAresta(verticeB);
            }
        }

        return null;
    }

    /**
     * Retorna se um grafo é completo ou não
     * 
     * @return boolean
     */
    public boolean completo() {
        for (Vertice vertice : this.vertices.allElements(new Vertice[this.vertices.size()])) {
            if (vertice.grau() != this.ordem() - 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Método que cria um subgrafo a partir de uma lista de vértices
     * 
     * @param Lista<Integer> - vertices
     * @return Grafo - subgrafo
     */
    public Grafo subGrafo(Lista<Integer> vertices) {
        Grafo subgrafo = new Grafo("Subgrafo de " + this.nome);
        Integer[] verticesArray = vertices.allElements(new Integer[vertices.size()]);
        for (Integer vertice : verticesArray) {
            subgrafo.addVertice(vertice);
        }
        for (Vertice vertice : this.vertices.allElements(new Vertice[this.vertices.size()])) {
            for (Aresta aresta : vertice.getArestas().allElements(new Aresta[vertice.getArestas().size()])) {
                if (subgrafo.existeVertice(aresta.destino()) != null) {
                    subgrafo.addAresta(vertice.getId(), aresta.destino(), aresta.peso());
                }
            }
        }

        return subgrafo;
    }

    /**
     * Retorna o tamanho do grafo
     * 
     * @return int
     */
    public int tamanho() {
        int tamanho = 0;
        for (Vertice vertice : this.vertices.allElements(new Vertice[this.vertices.size()])) {
            tamanho += vertice.getArestas().size();
        }

        return (tamanho / 2) + this.ordem();
    }

    /**
     * Retorna a ordem do grafo
     * 
     * @return int
     */
    public int ordem() {
        return this.vertices.size();
    }

    /**
     * Realiza uma busca em profundidade (DFS) no grafo a partir de um vértice de
     * origem.
     * 
     * @param origem O identificador do vértice de origem
     * @return Set<Integer> contendo os identificadores dos vértices visitados na
     *         ordem em que foram visitados
     */
    public Set<Integer> buscaEmProfundidade(int origem) {
        Set<Integer> visitados = new HashSet<>();
        Stack<Integer> pilha = new Stack<>();

        pilha.push(origem);

        while (!pilha.isEmpty()) {
            int verticeAtual = pilha.pop();

            if (!visitados.contains(verticeAtual)) {
                visitados.add(verticeAtual);

                Vertice vertice = this.vertices.find(verticeAtual);
                if (vertice != null) {
                    for (Aresta aresta : vertice.getArestas().allElements(new Aresta[vertice.getArestas().size()])) {
                        int destino = aresta.destino();
                        if (!visitados.contains(destino)) {
                            pilha.push(destino);
                        }
                    }
                }
            }
        }

        return visitados;
    }

    /**
     * Realiza uma busca em largura (BFS) no grafo a partir de um vértice de origem.
     * 
     * @param origem O identificador do vértice de origem
     * @return Set<Integer> contendo os identificadores dos vértices visitados na
     *         ordem em que foram visitados
     */
    public Set<Integer> buscaEmLargura(int origem) {
        Set<Integer> visitados = new HashSet<>();
        Queue<Integer> fila = new LinkedList<>();

        fila.offer(origem);

        while (!fila.isEmpty()) {
            int verticeAtual = fila.poll();

            if (!visitados.contains(verticeAtual)) {
                visitados.add(verticeAtual);

                Vertice vertice = this.vertices.find(verticeAtual);
                if (vertice != null) {
                    for (Aresta aresta : vertice.getArestas().allElements(new Aresta[vertice.getArestas().size()])) {
                        int destino = aresta.destino();
                        if (!visitados.contains(destino)) {
                            fila.offer(destino);
                        }
                    }
                }
            }
        }

        return visitados;
    }

    public Grafo arvoreGeradoraMinima(Integer idRaiz) {
        Grafo arvore = new Grafo("Árvore Geradora Mínima de " + this.nome);
        
        for(Vertice vertice:this.getVertices().allElements(new Vertice[this.getVertices().size()])){
            arvore.addVertice(vertice.getId());
        }

        // Vertice raiz = this.existeVertice(idRaiz);
        List<Integer> listSelecionados = new ArrayList<>();
        listSelecionados.add(idRaiz);

        List<ArestaWrapper> arestaAgm = new ArrayList<>();
        List<ArestaWrapper> listArestasGrafo = this.getArestas();

        ArestaWrapper minArestaAux;
        Double minPeso = Double.MAX_VALUE;
        while(listSelecionados.size() < this.getVertices().size()){
            
            minArestaAux = null;
            minPeso = Double.MAX_VALUE;
            Integer i = 0, minArestaIdx = 0;
            for(ArestaWrapper aresta:listArestasGrafo){
                if(listSelecionados.contains(aresta.origem) && !listSelecionados.contains(aresta.destino) && aresta.peso < minPeso){
                    minArestaAux = aresta;
                    minPeso = aresta.peso;
                    minArestaIdx = i;
                }
                i++;
            }
            if(minArestaAux == null) break;
            else {
                listSelecionados.add(minArestaAux.destino);
                arestaAgm.add(minArestaAux);
                listArestasGrafo.remove(minArestaIdx);
            }
        }

        for(ArestaWrapper aresta:arestaAgm){
            arvore.addAresta(aresta.origem, aresta.destino, aresta.peso);
        }

        return arvore;
    }

    private void imprime() {
        System.out.println("Árvore Geradora Mínima de " + this.nome);
        for (Vertice vertice : this.vertices.allElements(new Vertice[this.vertices.size()])) {
            for (Aresta aresta : vertice.getArestas().allElements(new Aresta[vertice.getArestas().size()])) {
                System.out.println(vertice.getId() + " - " + aresta.destino() + " (" + aresta.peso() + ")");
            }
        }
    }

    private int minimo(int[] vertices2, int[] pesos, int[] visitados) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < this.vertices.size(); i++) {
            if (visitados[i] == 0 && pesos[i] < min) {
                min = pesos[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    public String getNome() {
        return nome;
    }

    public ABB<Vertice> getVertices() {
        return vertices;
    }

    public List<ArestaWrapper> getArestas() {
        List<ArestaWrapper> listArestasTotal = new ArrayList<ArestaWrapper>();
        for(Vertice vertice:this.getVertices().allElements(new Vertice[this.getVertices().size()])){
            for(Aresta aresta:vertice.getArestas().allElements(new Aresta[vertice.getArestas().size()])){
                listArestasTotal.add(new ArestaWrapper(aresta.peso(), aresta.destino(), vertice.getId()));
            }
        }

        return listArestasTotal;
    }

    public static GrafoWrapper getGrafoWrapperFromGrafo(Grafo grafo) {
        List<VerticeWrapper> listVertices = new ArrayList<>();
        List<ArestaWrapper> listArestasTotal = new ArrayList<ArestaWrapper>();
        List<ArestaWrapper> listArestaAux; 
        List<Cidade> listCidadesVizinhasAux; 
        
        for(Vertice vertice:grafo.getVertices().allElements(new Vertice[grafo.getVertices().size()])){
            listArestaAux = new ArrayList<>();
            listCidadesVizinhasAux = new ArrayList<>();

            for(Aresta aresta:vertice.getArestas().allElements(new Aresta[vertice.getArestas().size()])){
                listArestaAux.add(new ArestaWrapper(aresta.peso(), aresta.destino(), vertice.getId()));
                listCidadesVizinhasAux.add(TpApplication.mapIdCidadeToCidade.get(aresta.destino()));
            }
            listArestasTotal.addAll(listArestaAux);
            listVertices.add(new VerticeWrapper(TpApplication.mapIdCidadeToCidade.get(vertice.getId()), listArestaAux, listCidadesVizinhasAux, listArestaAux.size()));
        }

        return new GrafoWrapper(listVertices, listArestasTotal);
    }

}
