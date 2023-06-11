package com.grafos.tp1.tp;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TpApplication {
	public static Grafo grafoTp;
	public static Map<Integer, Cidade> mapIdCidadeToCidade;
	public static void main(String[] args) {
		SpringApplication.run(TpApplication.class, args);
	}

}
