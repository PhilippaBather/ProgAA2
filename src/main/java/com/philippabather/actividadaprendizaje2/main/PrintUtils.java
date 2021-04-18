package com.philippabather.actividadaprendizaje2.main;

import com.philippabather.actividadaprendizaje2.domain.City;
import com.philippabather.actividadaprendizaje2.domain.Park;
import java.util.ArrayList;

/**
 * PrintUtils contiene métodos para imprimir detalles de las cuidades y parques
 * en el BBDD.
 * 
 * El nivel de aceso es package sólo.
 * 
 * @author philippa bather
 * 
 */
class PrintUtils {
    
    /**
     * Imprime en la consola una lista de cuidades.  Usado por el método 
     * totalArea en Menu.java relacionado con la opción del Menú 8.
     * 
     * @param cityArrList un array list de cuidades
     */
    public static void printCities(ArrayList<City> cityArrList) {
        cityArrList.forEach((city) -> {
            System.out.println(city.getCityName());
        });
        System.out.println();           //para formateo de la salida en la consola
    }
    
    /**
     * Imprime en la consola una lista de parques.  Usado por los métodos 
     * parksByCity y parksByCcca en Menu.java relacionados con las opciones del
     * Menú 1 y 2 respectivamente.
     * 
     * @param parkArrList un array list de parques
     */
    public static void printParks(ArrayList<Park> parkArrList) {
        parkArrList.forEach((park) -> {
            System.out.println(park.getName());
        });
        System.out.println();           //para formateo de la salida en la consola
    }
    
    /**
     * Imprime en la consola una lista de parques y sus cuidades correspondientes.
     * Usado por el método updatePark en Menu.java relacionado con la opción del
     * Menú 4.
     * 
     * @param parkArrList
     * @param cityArrList 
     */
    public static void printParksCities(ArrayList<Park> parkArrList, ArrayList<City> cityArrList) {
        for (int i = 0; i < parkArrList.size(); i++) {
            System.out.println("Parque ID: " + parkArrList.get(i).getParkId()
                    + "\n" + parkArrList.get(i) + "\nCuidad: " + cityArrList.get(i).getCityName());
            System.out.println();
        }
    }    
}
