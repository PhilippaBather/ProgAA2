package com.philippabather.actividadaprendizaje2.main;

import com.philippabather.actividadaprendizaje2.domain.City;
import com.philippabather.actividadaprendizaje2.domain.Park;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ValidateUtils contiene los métodos para validar y comprobar la entrada del
 * usuario.
 * 
 * El nivel de aceso es package sólo.
 * 
 * @author philippa bather
 * 
 */
class ValidateUtils {
    //declarar los variables
    private static Scanner sc = new Scanner(System.in);
    
    /**
     * Formatea la entrada del usuario como el formato usado en el BBDD.
     * 
     * @param input la entrada de los nombres de cuidades y parques
     * @return String
     */
    public static String formatStringInput (String input) {
        char[] charArr = input.toCharArray();
        boolean isSpace = true;
        
        for (int i = 0; i < charArr.length; i++) {
            //si el elemento del array es un carácter
            if (Character.isLetter(charArr[i])) {
                if (isSpace) {
                    //cambiar la letra a una mayúscula
                    charArr[i] = Character.toUpperCase(charArr[i]);
                    isSpace = false;
                }
            } else {
                isSpace = true;     //si el cáracter no es un espacio
            }
        }
        input = String.valueOf(charArr);
        
        return input;
    }
    
    /**
     * Comprueba si la entrada es un valor decimal (según el formato español)
     * de la extensión del parque.  Usado por los métodos areaGreaterThan y
     * totalArea en Menu.java relacionados con las opciones del Menú 6 y 8
     * respectivamente.
     * 
     * @return double
     */
    public static double validateArea () {
        boolean isDouble = false;
        double area = 0;
        
        while (!isDouble) {
            if (sc.hasNextDouble()) {
                //area = Double.parseDouble(sc.next());
                area = sc.nextDouble();
                break;
            } else {
                System.out.println("La entrada es inválida.");
                System.out.println("Introduzca un número decimal (locale españa): ");
                isDouble = false;
                sc.next();
            }
        }
        return area;
    }

    /**
     * Mueve al usuario a elegir la ID del parque que quiera actualizar si hay
     * registros multiples del nombre predeterminado del parque.  Se invoca
     * por el método updatePark relacionado con la opción del Menú 4 y llama
     * validateParkId abajo.
     * 
     * @return int
     */
    public static int selectPark(){
        System.out.println("\n¡Hay más de 1 parque con este nombre!");
        System.out.println("\nElegir la ID del parque que quieres modificar");
        int parkId = Integer.parseInt(sc.nextLine());        
        return parkId;
    }        
    
    /**
     * Valida la ID del parque introducida por el usuario.  Usado por el método
     * updatePark en Menu.java relacionado con la opción del Menú 4.
     * 
     * @param parkArrList el array list del parques
     * @param parkId la ID del parque introducida por el usuario
     * @return boolean
     */
    public static boolean validateParkId(ArrayList<Park> parkArrList, int parkId) {
        for (Park park : parkArrList) {
            if (parkId == park.getParkId()){     //comprobar si la seleccion es válida
                return true;
            } else {
                System.out.println("El parque ID no es válida.");
            }
        }
        return false;
    }
    
    /**
     * Mueve al usuario a elegir la ID de la cuidad que quiera actualizar si hay
     * registros multiples del nombre predeterminado del parque.  Se invoca
     * por el método updatePark relacionado con la opción del Menú 4 y llama
     * validateCityId abajo.
     * 
     * @return int
     */
    public static int selectCity(ArrayList city){
        boolean isValid = false;
        System.out.println("\n¡Hay más de 1 cuidad con este nombre!");
        System.out.println("\nElegir la ID de la cuidad que quieres modificar");
        int cityId = parseInt(sc.nextLine());
        
        while(!isValid) {
            System.out.println("\nElegir la ID de la cuidad que quieres modificar");
            int parkId = parseInt(sc.nextLine());
            isValid = validateCityId(city, cityId);
        }        
        return cityId;
    }        
    
    /**
     * Comprueba si la ID de la cuidad pasado como un parámetro existe en el
     * array list de cuidades.
     * 
     * @param cityArrList el array list de las cuidades
     * @param cityId la ID de la cuidad que se va a validar
     * @return boolean
     */
    public static boolean validateCityId(ArrayList<City> cityArrList, int cityId) {
        for (City city : cityArrList) {
            if (cityId == city.getCityId()){
                return true;
            } else {
                System.out.println("La ID de la cuidad no está válida.");
            }
        }
        return false;
    }    
    
    /**
     * Comprueba la entrada del usuario es 0 para actualizar un determinado 
     * registro del parque o 1 para continua.  Usado por el método updatePark
     * en Menu.java relacionad con la opción del Menú 4.
     * 
     * @return int
     */
    public static int validateUserResponse () {
        boolean isValid = false;
        int response = - 1;
        while (!isValid) {
            response = Integer.parseInt(sc.next());
            if (response == 0 || response == 1){
                    isValid = true;
            } else {
                System.out.println("La entrada es inválida.");
                System.out.println("Introduzca 0 para modificar o 1 para continuar.");
            }
        }
        return response;
    }
    
    /**
    * Este método devuele una mensaje del error por la consola en el caso de un
    * excepción del SQL y imprime la traza de la pila
    * 
    * @param sqle 
    */    
    public static void errorNotification(SQLException sqle) {
            System.out.println("SQL Error");
            sqle.printStackTrace();        
    }
}
