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

    @GetMapping("/get-agm/{id}")
    GrafoWrapper getAgm(@PathVariable Integer root){
        if(mapIntegerToAGM.containsKey(root) == false){
            Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp.arvoreGeradoraMinima(root));        
            return null;
        }else{
            return Grafo.getGrafoWrapperFromGrafo(mapIntegerToAGM.get(root));
        }
    }

    @GetMapping("/get-agm")
    GrafoWrapper getAgm(){
        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp.arvoreGeradoraMinima(1076135049));        
    }
}
