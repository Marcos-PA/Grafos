package com.grafos.tp1.tp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CidadeController {
    @GetMapping("/get-vertice/{id}")
    VerticeWrapper getGrafo(@PathVariable Integer id){
        return TpApplication.grafoTp.existeVertice(id).toWrapper();        
    }

}
