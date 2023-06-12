package com.grafos.tp1.tp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/get-caminho-minimo/")
    GrafoWrapper getCaminhoMinimo(){
        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp.arvoreGeradoraMinima(TpApplication.mapIdCidadeToCidade.get(TpApplication.mapIdCidadeToCidade.keySet().toArray()[0]).id));        
    }
}
