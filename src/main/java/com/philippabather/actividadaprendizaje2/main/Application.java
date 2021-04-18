package com.philippabather.actividadaprendizaje2.main;

import com.philippabather.actividadaprendizaje2.domain.Gardener;
import java.sql.SQLException;

/**
 * Está aplicación gestiona los parques de España.
 * 
 * Application arranca el programa ejecutando el menú.
 * 
 * @author philippa bather
 */
public class Application {
    
    public static void main(String[] args) throws SQLException {
    
        Menu parque = new Menu();
        parque.executeMenu();
    }
}
