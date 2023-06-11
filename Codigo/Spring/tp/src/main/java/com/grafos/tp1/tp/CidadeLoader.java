package com.grafos.tp1.tp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CidadeLoader {
    @Bean
    CommandLineRunner loadCidades(){
        return args -> {
            long start = System.nanoTime();
            System.out.print("Carregando cidades... ");

            Grafo grafo = new Grafo("Cidades");

            LeitorCsv csv = new LeitorCsv("src/main/java/com/grafos/tp1/tp/cidades.csv");
            Map<Integer, Map<Integer, List<Cidade>>> mapLatToMapLongToCidade = new HashMap<>();

            Cidade cidadeAux;
            Map<Integer, List<Cidade>> mapLongAux;
            Map<Integer, Cidade> mapIdToCidade = new HashMap<>();
            while(csv.hasNextLine()){
                int latKey, longKey;
                cidadeAux = new Cidade(csv.nextLine());
                latKey = (int) cidadeAux.latitude;
                longKey = (int) cidadeAux.longitude;

                if(mapLatToMapLongToCidade.containsKey(latKey)){
                    mapLongAux = mapLatToMapLongToCidade.get(latKey);
                }else{
                    mapLongAux = new HashMap<>();
                }

                if(mapLongAux.containsKey(longKey)){
                    mapLongAux.get(longKey).add(cidadeAux);
                }else{
                    mapLongAux.put(longKey, new ArrayList<>(Arrays.asList(cidadeAux)));
                }

                mapLatToMapLongToCidade.put(latKey, mapLongAux);
                mapIdToCidade.put(cidadeAux.id, cidadeAux);
            }

            Integer offset, lastRunController;

            Stack<Integer> stackIdCidadesProximas;
            Map<String, Double> mapKeyArestaParaDistancia = new HashMap<>();
        
            String keyAux, inverseKeyAux;
            Double distanciaAux;
            for(Integer latKey:mapLatToMapLongToCidade.keySet()){
                for(Integer longKey:mapLatToMapLongToCidade.get(latKey).keySet()){
                    for(Cidade cidadeVertice:mapLatToMapLongToCidade.get(latKey).get(longKey)){
                        stackIdCidadesProximas = new Stack<>();
                        offset = 0;
                        // Alterará para 1 na última rodagem devido ao lastRunController++ na condição do while. 
                        lastRunController = 0;

                        while(stackIdCidadesProximas.size() < 4 || lastRunController++ == 0){
                            for(Integer offsetLat = -offset; offsetLat <= offset; offsetLat += 1){
                                if(mapLatToMapLongToCidade.get(latKey + offsetLat) == null) continue;
                                for(Integer offsetLong = -offset; offsetLong <= offset; offsetLong += 1){
                                    if(mapLatToMapLongToCidade.get(latKey + offsetLat).get(longKey + offsetLong) == null) continue;

                                    for(Cidade cidadeJ:mapLatToMapLongToCidade.get(latKey + offsetLat).get(longKey + offsetLong)){
                                        if(cidadeJ == cidadeVertice) continue;

                                        keyAux = String.valueOf(cidadeVertice.id) + '-' + String.valueOf(cidadeJ.id);
                                        inverseKeyAux = String.valueOf(cidadeJ.id) + '-' + String.valueOf(cidadeVertice.id);
                                        
                                        if(mapKeyArestaParaDistancia.get(inverseKeyAux) != null){
                                            distanciaAux = mapKeyArestaParaDistancia.get(inverseKeyAux);
                                        }else{
                                            distanciaAux = Haversine.calcularDistancia(cidadeVertice.latitude, cidadeVertice.longitude, cidadeJ.latitude, cidadeJ.longitude);
                                        }
                                        
                                        mapKeyArestaParaDistancia.put(String.valueOf(keyAux), distanciaAux);
                                        

                                        Integer idxCidadeProx = 0;
                                        for(Integer idCidade : stackIdCidadesProximas){
                                            if(idxCidadeProx > 4 || mapKeyArestaParaDistancia.get(String.valueOf(cidadeVertice.id) + '-' + String.valueOf(idCidade)) > distanciaAux){
                                                break;
                                            }
                                            idxCidadeProx++;
                                        }

                                        if(!stackIdCidadesProximas.contains(cidadeJ.id)) stackIdCidadesProximas.add(idxCidadeProx, cidadeJ.id);
                                    }

                                    if(offset == 0) break;
                                }

                                if(offset == 0) break;
                            }
                            offset++;
                        }

                        stackIdCidadesProximas.setSize(4);
                        stackIdCidadesProximas.trimToSize();

                        grafo.addVertice(cidadeVertice.id);

                        for(Integer idCidade:stackIdCidadesProximas){
                            grafo.addAresta(cidadeVertice.id, idCidade, mapKeyArestaParaDistancia.get(String.valueOf(cidadeVertice.id) + "-" + String.valueOf(idCidade)));
                        }
                    }
                }
            }
            long end = System.nanoTime();

            TpApplication.grafoTp = grafo;
            TpApplication.mapIdCidadeToCidade = mapIdToCidade;
            System.out.println("Finalizado em " + (end-start)/1000000 + "ms");
        };
    }
}
