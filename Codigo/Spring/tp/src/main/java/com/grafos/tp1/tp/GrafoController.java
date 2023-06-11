package com.grafos.tp1.tp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrafoController {
    @GetMapping("/get-grafo")
    GrafoWrapper getGrafo(){
        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp);        
    }

    @GetMapping("/get-agm")
    GrafoWrapper getAgm(){
        return Grafo.getGrafoWrapperFromGrafo(TpApplication.grafoTp.arvoreGeradoraMinima(1076135049));        
    }
}
