package com.grafos.tp1.tp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrafoController {
    @GetMapping("/get-grafo")
    GrafoWrapper getGrafo(){

        return new GrafoWrapper();
    }
}
