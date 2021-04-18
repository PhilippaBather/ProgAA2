package com.philippabather.actividadaprendizaje2.main;

import com.philippabather.actividadaprendizaje2.dao.*;
import com.philippabather.actividadaprendizaje2.domain.City;
import com.philippabather.actividadaprendizaje2.domain.Park;
import static com.philippabather.actividadaprendizaje2.main.PrintUtils.*;
import static com.philippabather.actividadaprendizaje2.main.ValidateUtils.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Menu contiene los métodos principales de la aplicación que imprime el menú
 * de opciones en la consola y realiza la opción selecionada por el usuario.
 * 
 * @author philippa bather
 */
public class Menu {   
    //declarar la propiedades de la instancia   
    private boolean exit;
    private Scanner input;
    private Conexion con;
    private ParkDAO parkDAO;
    private CityDAO cityDAO;
    
    //constructor    
    public Menu() throws SQLException {
        exit = false;
        input = new Scanner(System.in);
        con = new Conexion();                                                   //se conecta a la BBDD
        con.connect();        
        cityDAO = new CityDAO(con);
        parkDAO = new ParkDAO(con);
    }
    
    /**
     * Este método visualiza las opciónes del menú en la consola y invoca los
     * métodos corespondientes a la selección del usuario.
     */
    public void executeMenu() {
        do {
            System.out.println("*\t\t\t\tParques\t\t\t\t*\n");
            System.out.println("1. Listar los parques de una ciudad por nombre");
            System.out.println("2. Listar los parques de CCAA por nombre");
            System.out.println("3. Insertar un parque");
            System.out.println("4. Actualizar un parque");
            System.out.println("5. Seleccionar todos los parques por una busqueda de cadena");
            System.out.println("6. Mostrar el número de parques de una ciudad con una extension mínima");
            System.out.println("7. Borrar todos los parques de una cuidad por nombre");
            System.out.println("8. Listar las ciudades con una extensión total mínima");
            System.out.println("x. Salir");
            System.out.println("\nIntroduzca tu seleccion: ");
            String entrada = input.nextLine().toLowerCase();
            System.out.println();
 
            switch (entrada) {
                case "1":
                    parksByCity();
                    break;
                case "2":
                    parksByCCAA();
                    break;
                case "3":
                    addPark();     
                    break;    
                case "4":
                    updatePark();
                    break;                    
                case "5":
                    searchParksByString();
                    break;
                case "6":
                    areaGreaterThan();
                    break;                    
                case "7":
                    deleteParks();
                    break;                    
                case "8":
                    totalArea();
                    break;
                case "x":
                    exit();
                    break;
                default:
                    System.out.println("Entrada no reconocida.\n");
            }
        } while(!exit);
    }
    
    /**
     * Lista todos los parques de una determinado cuidad por nombre por invocando
     * los métodos parksInCity en el paquete DAO y printParks en la clase
     * PrintUtils.java.  Se refiere al Menú Opción 1.
     */
    public void parksByCity() {
        System.out.println("Introduzca el nombre de la cuidad: ");
        String city = formatStringInput(input.nextLine().toLowerCase());        //formatea la entrada       
                
        try {
            ArrayList<Park> parksArrList = parkDAO.parksInCity(city);
            System.out.println("\nParques en " + city + ": ");
            printParks(parksArrList);
        } catch (SQLException sqle) {
            errorNotification(sqle);
        }
    }
    
    /**
     * Lista todos los parques de una cierta Comunidad Autónoma por nombre por
     * invocando los métodos parksInCcaa en el paquete DAO y printParks en 
     * PrintUtils.java.  Se refiere al Menú Opción 2.
     */
    public void parksByCCAA() {
        System.out.println("Nombre de la Comunidad Autónoma: ");
        String ccaa = formatStringInput(input.nextLine().toLowerCase());        //formatea la entrada
        
        try {
            ArrayList<Park> parksArrList = parkDAO.parksInCcaa(ccaa);
            System.out.println("\nParques en la CCAA " + ccaa + ": ");
            printParks(parksArrList);            
        } catch (SQLException sqle) {
            errorNotification(sqle);
        }   
    }
       
    /**
     * Añade un parque a una determinada cuidad (por nombre) siempre y cuando
     * la cuidad exista en el BBDD; el procedimiento se refiere a Menú Opción 3.     * 
     */   
    public void addPark() {
        ArrayList<City> cityArrList = searchCitiesByName();                     //obtiene los detalles de la cuidad introducida por el usuario

        if(!cityArrList.isEmpty()){                                             //si la cuidad existe
            System.out.println("Introduzca el nombre del parque: ");            //obtiene las propiedades y crea el objeto Park
            String name = formatStringInput(input.nextLine());
            System.out.println("Introduzca la extensión del parque: ");            
            double area = validateArea();
            int cityId = cityArrList.get(0).getCityId();
            Park park = new Park(cityId, name, area);
            try {                                                               //añade el parque al BBDD sólo si no es una duplicación
                boolean isDuplicate = parkDAO.isDuplicate(park);
                if (!isDuplicate) {                  
                    parkDAO.insertIntoPark(park);
                    System.out.println("\nEl parque ha sido insertado.");
                }
            } catch (SQLException sqle) {
                errorNotification(sqle);                        
            }          
        } else {                                                                //si la cuidad NO existe, notifica al usuario
            System.out.println("\n*** La cuidad no está reconocida. ***");            
        }
    }    
 
    /**
     * Facilita las actualizaciones requeridos por el usuario.     * 
     */
    public void updatePark() {
        boolean isValid = true;                                         
        int parkId; 
        
        System.out.println("Introduzca el nombre del parque quieres actualizar: ");
        String parkName = formatStringInput(input.nextLine().toLowerCase());    //guarda el nombre formateado
        
        //invoca el método para comprueba si el nombre del parque introducido por el usuario existe y devolver una array de parques de este nombre
        ArrayList<Park> parkArrList = isPark(parkName);
        
        if(!parkArrList.isEmpty()) {
            ArrayList<City> cityArrList = searchCitiesByPark(parkName);     //contiene cuidades en las que el parque predeterminado existe
            printParksCities(parkArrList, cityArrList);                         //imprime los parques y las cuidades correspondientes
             
            if (parkArrList.size() > 1) {                                       //si hay registros multiples del nombre predeterminado
                parkId = selectPark();                                          //invoca el método para que el usuario selecione la ID
                isValid = validateParkId(parkArrList, parkId);                  //valida la ID introducida del parque
            } else {
                parkId = parkArrList.get(0).getParkId();            //else: asigna el valor la ID del parque del único parque registrado a parkId
            }
            
            //si hay sólo un parque registrado (isValid es true por defecto) o la ID introducida es válido, obtiene las actualizaciones
            if(isValid) {
                System.out.println("\nIntroduzca 0 para actualizar el nombre del parque o 1 para continuar.");
                int response = validateUserResponse();
                if (response == 0) {
                    updateParkName(parkId);
                }
                System.out.println("Introduzca 0 para actualizar la extensión del parque o 1 para continuar.");
                response = validateUserResponse();
                if (response == 0) {
                    updateParkArea(parkId);
                }
                System.out.println("Introduzca 0 para actualizar la cuidad del parque o 1 para continuar.");
                response = validateUserResponse();
                if (response == 0) {
                    updateCityName(parkId);
                }
            }
        }else {
            System.out.println("El parque no existe en el BBDD.\n");
        }
    }
    
    /**
     * Comprueba si un parque del nombre pasado en el parámetro existe en el BBDD.
     * Se invoca por el método updatePark relacionado con la opción del Menú 4.
     * 
     * @param name el nombre del parque
     * @return ArrayList del objeto Park
     */
    public ArrayList<Park> isPark(String name) {
        ArrayList<Park> parkArrList = new ArrayList<>();
        try {
            parkArrList = parkDAO.isPark(name);
        } catch (SQLException sqle) {
            errorNotification(sqle);
        }
        return parkArrList;
    }
    
    /**
     * Devuele a un array list todas las cuidades en las que hay un parque con 
     * el nombre pasado en el parámetro.  Se invoca por el método updatePark
     * relacionada con la opción del Menú 4.
     * 
     * @param parkName el nombre del parque que el usuario quiere actualizar
     * @return ArrayList del objeto City
     */
    public ArrayList<City> searchCitiesByPark(String parkName) {
        ArrayList<City> cityArrList = new ArrayList<>();
        try {
            cityArrList = cityDAO.getCitiesByPark(parkName);
        } catch (SQLException sqle) {
            errorNotification(sqle);
        }
        return cityArrList;
    }
       
    /**
     * Obtiene el nombre del parque y invoca el método updateParkName en el paquete
     * domain para actualizarlo.  Usado por el método updatePark relacionado con
     * la opción del Menú 4.
     * 
     * @param parkId la ID del parque
     */
    public void updateParkName(int parkId) {
        System.out.println("Nombre del parque: ");
        String parkName = formatStringInput(input.nextLine().toLowerCase());    //formatea la entrada
        
        try {
            parkDAO.updateParkName(parkName, parkId);
        } catch (SQLException sqle) {
            errorNotification(sqle);
        }
    }
    
    /**
     * Obtiene la extensión del parque y invoca el método updateParkArea en el
 paquete domain para actualizarlo. Usado por el método updatePark
     * relacionado con la opción del Menú 4.
     * 
     * @param parkId la ID del parque
     */
    public void updateParkArea(int parkId) {
        System.out.println("Introduzca la extensión del parque: ");
        double parkArea = validateArea();

        try {
            parkDAO.updateParkArea(parkArea, parkId);
        } catch (SQLException sqle) {
            errorNotification(sqle);
        }         
    }
    
    /**
     * Obtiene el nombre de la cuidad del parque y invoca el método updateParkName
     * en el paquete domain para actualizarlo. Usado por el método updatePark
     * relacionado con la opción del Menú 4.
     * 
     * @param parkId la ID del parque
     */   
    public void updateCityName(int parkId) {
        System.out.println("Introduzca el nombre de la cuidad: ");
        String name = formatStringInput(input.nextLine().toLowerCase());        //formatea la entrada
        
        try {
            ArrayList<City> cityArrList = cityDAO.getCityRecords(name);
            int cityId;
            //si la cuidad no existe en la BBDD, añadelo
            if (cityArrList.isEmpty()){
                System.out.println("La cuidad no está registrado.");            //notifica al usuario
                System.out.println("Introduzca la Comunidad Autónoma: ");       //obtiene la CCAA relacionada
                String ccaa = formatStringInput(input.nextLine().toLowerCase());//formatea la entrada
                cityDAO.insertIntoCity(name, ccaa);                             //añade el registro a la BBDD en la tabla CUIDAD
                cityArrList = cityDAO.getCityRecords(name);                     //obtiene el registro y desde la BBDD
                cityId = cityArrList.get(0).getCityId();                        //y pone el valor la ID de la nueva cuidad
            } else{
                if (cityArrList.isEmpty()) {
                    cityId = cityArrList.get(0).getCityId();                    //pone el valor de la ID de la cuidad
                } else {                                                        //else -else: si más de 1 cuidad
                    cityId = selectCity(cityArrList);                           //pone el valor de la ID de la cuidad elegida por el usuario
                }
            }
            parkDAO.updateParkCityId(cityId, parkId);                              //actualiza el registro de la CUIDAD_ID en la tabla PARQUE
        } catch (SQLException sqle) {
                errorNotification(sqle);
        }
   
    }

    /**
    * Identifica todos los parques cuyo nombre contenga una determinada cadena
    * entrada por el usuario y invoca el método printParksCities para imprimir
    * los resultados (los parques y sus cuidades correspondientes) en la
    * consola; se refiere al Menú Opción 5.
    */
    public void searchParksByString() {        
        System.out.println("Introduzca la cadena con que quieres buscar parques: ");
        String searchStr = input.nextLine();
        
        ArrayList<Park> parkArrList = new ArrayList<>();
        ArrayList<City> cityArrList = new ArrayList<>();
        try {
            parkArrList = parkDAO.searchByStringParks(searchStr);
            cityArrList = cityDAO.cityStringSearch(searchStr);
        } catch (SQLException sqle) {
            errorNotification(sqle);
        }        
        printParksCities(parkArrList, cityArrList);
    }
    
    /**
     * Devuelve el número de parques de una determinada cuidad que tengan una
     * extensión individual mayor que lo que introduzca el usuario; se refiero
     * al Menú Opción 6.
     */
    public void areaGreaterThan() {
        ArrayList<City> cityArrList = searchCitiesByName();
        if (!cityArrList.isEmpty()) {                                           //si la cuidad existe
            double area = validateArea();                                       //obtiene y valida el área
            try {                                                               //imprime el núermo de parques
                System.out.println("\nNúmero de parques en " + cityArrList.get(0).getCityName() 
                        + " con una extensión más de "           
                        + area + ": " + parkDAO.parkArea(cityArrList.get(0).getCityId(), area));
                System.out.println();
            } catch (SQLException sqle) {
                errorNotification(sqle);
            }
        } else {
            System.out.println("La cuidad no está reconocida.\n");
        }       
    }    

    /**
    * Borra todos los parques de una determinada cuidad introducida por el
    * usuario; se refiere al Menú Opción 7. 
    */
    public void deleteParks() {
        ArrayList<City> cities = searchCitiesByName();
        if (!cities.isEmpty()) {
            try {
                parkDAO.deleteParks(cities.get(0));
            } catch (SQLException sqle) {
                errorNotification(sqle);
            }
        } else {
            System.out.println("\n**** La cuidad no esta reconocida. ****\n");            
        }
    }
    
    /**
     * Busca en la tabla CUIDAD para la cuidad introducida por el usuario.  Se
     * invoca por los métodos areGreaterThan y deletParks relacionados con las
     * opciones del Menú 6 y 7 respectivamente
     * 
     * @return ArrayList del objeto City
     */           
    public ArrayList<City> searchCitiesByName() {
        System.out.println("Nombre de la cuidad: ");
        String cityName = formatStringInput(input.nextLine().toLowerCase());    //formateo la etnrada
        
        //comprobamos si el existe existe y si hay más que uno en el BBDD        
        ArrayList<City> cityArrList = new ArrayList<>();
        try {
            cityArrList = cityDAO.getCityRecords(cityName);
        } catch (SQLException sqle) {
            errorNotification(sqle);
        }
        return cityArrList;  
    }    
    
    /**
     * Lista el nombre de todas las cuidades que contengan parques 
     * cuya suma total de su área sea mayor que la introduica por el usuario. Se
     * refiere a Menú Opción 8.
     */
    public void totalArea() {
        System.out.println("Introduzca la extensíon mínima: ");
        double areaTotal = validateArea();
        try {
            ArrayList<City> cities = cityDAO.totalArea(areaTotal);
            System.out.println("Las cuidades con una extension más de " + areaTotal + ": \n");
            printCities(cities);
        } catch (SQLException sqle) {
            errorNotification(sqle);
        }
    }
    
    /**
     * Disconecta la conexión del BBDD y termina la aplicación; se refiere al
     * Menú Opción X.
     */
    public void exit() {
        con.disconnect();
        exit = true;
    }
}