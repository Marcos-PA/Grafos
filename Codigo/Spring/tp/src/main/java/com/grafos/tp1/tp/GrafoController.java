package com.grafos.tp1.tp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrafoController {
    public static Map<Integer, Grafo> mapIntegerToAGM = new HashMap<>();

    @GetMapping("/get-grafo")
    GrafoWrapper getGrafo(){
        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp);        
    }

    @GetMapping("/get-agm/{root}")
    GrafoWrapper getAgm(@PathVariable int root){
        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp.arvoreGeradoraMinima(root));
    }

    @GetMapping("/get-agm")
    GrafoWrapper getAgm(){
        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp.arvoreGeradoraMinima(TpApplication.mapIdCidadeToCidade.get(TpApplication.mapIdCidadeToCidade.keySet().toArray()[0]).id));        
    }

    @GetMapping("/get-caminho-minimo/{root}/{end}")
    GrafoWrapper getCaminhoMinimo(@PathVariable Integer root, @PathVariable Integer end){
        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp.caminhoMinimo(root, end));        
    }

    @GetMapping("/get-caminho-minimo")
    GrafoWrapper getCaminhoMinimo(){
        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp.arvoreGeradoraMinima(TpApplication.mapIdCidadeToCidade.get(TpApplication.mapIdCidadeToCidade.keySet().toArray()[0]).id));        
    }

    @GetMapping("/remove-aresta/{idOrigem}/{idDestino}")
    GrafoWrapper removerAresta(@PathVariable Integer idOrigem, @PathVariable Integer idDestino){
        TpApplication.grafoTp.removeAresta(idOrigem, idDestino);
        TpApplication.grafoTp.removeAresta(idDestino, idOrigem);

        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp);        
    }

    @GetMapping("/remove-vertice/{idVertice}")
    GrafoWrapper removerAresta(@PathVariable Integer idVertice){
        TpApplication.grafoTp.removeVertice(idVertice);
        
        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp);        
    }
    
    @GetMapping("/busca-profundidade/{origem}")
    Set<Integer> buscaProfundidade(@PathVariable Integer origem){
        Set<Integer> setResultado = TpApplication.grafoTp.buscaEmProfundidade(origem);
        return setResultado;        
    }

    @GetMapping("/busca-largura/{origem}")
    Set<Integer> buscaLargura(@PathVariable Integer origem){
        Set<Integer> setResultado = TpApplication.grafoTp.buscaEmLargura(origem);
        return setResultado;        
    }
}
