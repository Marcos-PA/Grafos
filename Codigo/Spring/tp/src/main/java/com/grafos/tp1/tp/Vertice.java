package com.grafos.tp1.tp;
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

import java.util.ArrayList;
import java.util.List;

/** Classe Vertice para um grafo */
public class Vertice {

    private ABB<Aresta> arestas;
    private final int id;
    private boolean visitado;

    /**
     * Construtor para criação de vértice identificado
     * 
     * @param id Número/id do vértice a ser criado (atributo final).
     */
    public Vertice(int id) {
        this.id = id;
        this.arestas = new ABB<Aresta>();
        this.visitado = false;
    }

    /**
     * Retorna o id do vértice, caso seja necessário para verificações próprias
     * 
     * @return Identificador do vértice (int)
     */
    public int getId() {
        return this.id;
    }

    // *Para a adição de arestas, temos de pensar uma maneira, pois de acordo com a
    // *regra o vertice(cidade)so pode fazer ligações com as quatro cidades mais
    // *proximas a ele*/

    /**
     * Retorna a lista de arestas
     * 
     * @return
     */
    public ABB<Aresta> getArestas() {
        return this.arestas;
    }

    /**
     * Retorna a lista de arestas
     * 
     * @return
     */
    public List<ArestaWrapper> getArestasWrapper() {
        List<ArestaWrapper> listArestasTotal = new ArrayList<ArestaWrapper>();
        for(Aresta aresta:this.getArestas().allElements(new Aresta[this.getArestas().size()])){
            listArestasTotal.add(new ArestaWrapper(aresta.peso(), aresta.destino(), this.getId()));
        }

        return listArestasTotal;
    }


    // *Para este metodo de adição de arestas, precisamos criar um codigo que
    // *calcule a distancia usando as diferenças entre latitudes e longitudes, que
    // *seram os dados disponibilizados no arquivo para calculo do grafo*/

    /**
     * Adiciona uma aresta ponderada neste vértice para um destino
     * 
     * @param peso Peso da aresta, neste caso sendo referente a distancia entre as
     *             cidades
     * @param dest Vértice de destino, cidades vizinhas
     * @return TRUE se foi inserida, FALSE caso já existisse e não foi inserida.
     */
    public boolean addAresta(int destino, double peso) {
        return this.arestas.add(destino, new Aresta(peso, destino));
    }

    /**
     * Adiciona uma aresta não ponderada neste vértice para um destino
     * 
     * @param dest Vértice de destino
     * @return TRUE se foi inserida, FALSE caso já existisse e não foi inserida.
     */
    public boolean addAresta(int destino) {
        return this.arestas.add(destino, new Aresta(-1, destino));
    }

    public Aresta existeAresta(int destino) {
        return this.arestas.find(destino);
    }

    /**
     * Remove e retorna a aresta para o destino indicado. Retorna null caso não
     * exista a aresta.
     * 
     * @param destino Destino da aresta a ser removida.
     * @return A aresta removida, ou null se não existir.
     */
    public Aresta removeAresta(int destino) {
        return this.arestas.remove(destino);
    }

    public int grau() {
        return this.arestas.size();
    }

    /**
     * Marca o vértice como visitado
     */
    public void visitar() {
        this.visitado = true;
    }

    /**
     * Marca o vértice como não visitado
     */
    public void limparVisita() {
        this.visitado = false;
    }

    /**
     * Indica se o vértice foi visitado (TRUE) ou não (FALSE)
     * 
     * @return TRUE/FALSE conforme o vértice tenha sido ou não visitado.
     */
    public boolean visitado() {
        return this.visitado;
    }

    public VerticeWrapper toWrapper() {
        List<ArestaWrapper> listArestaAux = new ArrayList<>(); 
        List<Cidade> listCidadesVizinhasAux = new ArrayList<>();
        for(Aresta aresta:this.getArestas().allElements(new Aresta[this.getArestas().size()])){
            listArestaAux.add(new ArestaWrapper(aresta.peso(), aresta.destino(), this.getId()));
            listCidadesVizinhasAux.add(TpApplication.mapIdCidadeToCidade.get(aresta.destino()));
        }

        return new VerticeWrapper(TpApplication.mapIdCidadeToCidade.get(this.getId()), listArestaAux, listCidadesVizinhasAux, listArestaAux.size());
    }
}
