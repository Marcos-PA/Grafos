package com.grafos.tp1.tp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrafoController {
    @GetMapping("/get-grafo")
    GrafoWrapper getGrafo(){
        List<VerticeWrapper> listVertices = new ArrayList<>();
        Grafo grafo = TpApplication.grafoTp;

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
