package com.grafos.tp1.tp;

import java.util.HashMap;
import java.util.Map;

public class Cidade {
    public static int idIncrement = 0;

    public Cidade(Map<String, String> cidadeMap){
        this.nome = cidadeMap.get("city");
        this.nomeAscii = cidadeMap.get("city_ascii");
        this.latitude = Double.parseDouble(cidadeMap.get("lat"));
        this.longitude = Double.parseDouble(cidadeMap.get("lng"));
        this.pais = cidadeMap.get("country");
        this.isoPais = cidadeMap.get("iso2");
        this.estado = cidadeMap.get("admin_name");
        this.populacao = cidadeMap.get("population") != null && !cidadeMap.get("population").equals("") ? Double.parseDouble(cidadeMap.get("population")) : 0;
        this.id = cidadeMap.get("id") != null ? Integer.parseInt(cidadeMap.get("id")) : idIncrement++;
    }

    public String nome;
    public String nomeAscii;
    public double latitude;
    public double longitude;
    public String pais;
    public String isoPais;
    public String estado;
    public double populacao;
    public Integer id;
}
