package Codigo;

import java.lang.Math;

public class Haversine {

    /**
     * método estático calcularDistancia que recebe as coordenadas de latitude e
     * longitude de dois pontos e retorna a distância em quilômetros entre esses
     * pontos, utilizando a fórmula de Haversine.
     * 
     * @param lat1 Latitude da primeira localização
     * @param lon1 Longitude da primeira localização
     * @param lat2 Latitude da segunda localização
     * @param lon2 Longitude da segunda localização
     * @return Distancia entre as duas localizações
     */
    public static double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        // Raio da Terra em quilômetros
        double raioTerra = 6371;

        // Conversão para radianos
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferença entre as longitudes e latitudes
        double dLon = lon2Rad - lon1Rad;
        double dLat = lat2Rad - lat1Rad;

        // Fórmula de Haversine
        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distancia = raioTerra * c;

        return distancia;
    }
}
