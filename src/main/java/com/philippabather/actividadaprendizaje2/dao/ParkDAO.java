package com.philippabather.actividadaprendizaje2.dao;

import com.philippabather.actividadaprendizaje2.domain.City;
import com.philippabather.actividadaprendizaje2.domain.Park;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ParkDAO contiene las consultas de la tabla Parque en la BBDD.
 * 
 * @author philippa bather
 */
public class ParkDAO {

    //declarar las propiedades de la instancia
    private Conexion con;
   
    //constrctuor
    public ParkDAO(Conexion conexion) {
        this.con = conexion;
    }
    
    /**
     * Acepta como su parámetro el ResultSet de una consulta y rellena un array
     * list de objeto Park con los resultados.
     * 
     * @param result
     * @return ArrayList de objetos Park
     * @throws SQLException 
     */
    public ArrayList<Park> returnParkArrayList(ResultSet result) throws SQLException {
        ArrayList<Park> parks = new ArrayList<>();
        
        while (result.next()) {
            Park park = new Park();
            park.setParkId(result.getInt(1));
            park.setCityId(result.getInt(2));
            park.setName(result.getString(3));
            park.setArea(result.getDouble(4));
            parks.add(park);
        }       
        return parks;
    }
    
    /**
     * Realiza una búsqueda SQL para obtener una lista de parques de una cuidad
     * predeterminada.
     * 
     * @param city la cuidad en la que la búsqueda depende
     * @return ArrayList de objetos Park
     * @throws SQLException 
     */
    public ArrayList<Park> parksInCity(String city) throws SQLException {
        String sql = "SELECT * FROM PARQUE P "
                + "INNER JOIN CUIDAD C ON P.CUIDAD_ID = C.CUIDAD_ID "
                + "WHERE C.CUIDAD_NOMBRE = ? "
                + "ORDER BY PARQUE_NOMBRE"; 
                
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, city);
        ResultSet result = statement.executeQuery();
        
        ArrayList<Park> parks = returnParkArrayList(result);

        return parks;
    }
    
    /**
     * Realiza una búsqueda SQL para obtener una lista de parques de una CCAA
     * predeterminada
     * 
     * @param ccaa la CCAA en la que la búsqueda depende
     * @return ArrayList de objetos Park
     * @throws SQLException 
     */
    public ArrayList<Park> parksInCcaa(String ccaa) throws SQLException {
        String sql = "SELECT * FROM PARQUE P "
                + "INNER JOIN CUIDAD C ON P.CUIDAD_ID = C.CUIDAD_ID "
                + "WHERE C.CCAA = ? "
                + "ORDER BY PARQUE_NOMBRE";
                
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, ccaa);
        ResultSet result = statement.executeQuery();
        
        ArrayList<Park> parks = returnParkArrayList(result);
        
        return parks;
    }
    
    /**
     * Comprueba si el parque objeto pasado por el parámetro que el usuario 
     * quiere añade existe ya en la BBDD.
     * 
     * @param newPark
     * @return boolean
     * @throws SQLException 
     */
    public boolean isDuplicate(Park newPark) throws SQLException {
        String sql = "SELECT * FROM PARQUE "
                + "WHERE PARQUE_NOMBRE = ?";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, newPark.getName());
        ResultSet result = statement.executeQuery();
        
        ArrayList<Park> parkArrList = returnParkArrayList(result);
        
        //park.toString() comprueba duplicados basado en la ID de la cuidad, su nombre y la éxtension
        for (Park park : parkArrList) {
            if (park.toString().equals(newPark.toString())) {
                System.out.println("El parque ya existe en el BBDD.");
                return true;
            }
        }       
        return false;
    }
    
    /**
     * Realiza una inserción en la BBDD de un nuevo objeto Park.
     * 
     * @param newPark
     * @throws SQLException 
     */
    public void insertIntoPark(Park newPark) throws SQLException {
        String sql = "INSERT INTO PARQUE(CUIDAD_ID, PARQUE_NOMBRE, EXTENSION) VALUES(?, ?, ?)";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setInt(1, newPark.getCityId());
        statement.setString(2, newPark.getName());
        statement.setDouble(3, newPark.getArea());
        statement.executeUpdate();
    }
    
    /**
     * Comprueba si el parque pasado por el parámetro existe.
     * 
     * @param parkName
     * @return ArrayList de objetos Park
     * @throws SQLException 
     */
    public ArrayList<Park> isPark(String parkName) throws SQLException {
        String sql = "SELECT p.PARQUE_ID, p.CUIDAD_ID, p.PARQUE_NOMBRE, p.EXTENSION, c.CUIDAD_NOMBRE FROM PARQUE p "
                + "INNER JOIN CUIDAD c ON p.CUIDAD_ID = c.CUIDAD_ID "          
                + "WHERE PARQUE_NOMBRE = ? ";
               
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, parkName);
        ResultSet result = statement.executeQuery();

        ArrayList<Park> parks = returnParkArrayList(result);
        
        return parks;
    }
    /**
     * Actualiza un registro de un parque predeterminado en la BBDD.
     * 
     * @param originalPark
     * @param newPark
     * @throws SQLException 
     */
    public void updatePark(Park originalPark, Park newPark) throws SQLException {
        String sql = "UPDATE PARQUE SET CUIDAD_ID = ?, PARQUE_NOMBRE = ?, EXTENSION = ? "
                + " WHERE PARQUE_ID = ?";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setInt(1, newPark.getCityId());
        statement.setString(2, newPark.getName());
        statement.setDouble(3, newPark.getArea());
        statement.setInt(4, originalPark.getParkId());
        statement.executeUpdate();
    }
    
    /**
     * Actualiza el nombre de un parque predeterminado en la BBDD.
     * 
     * @param parkName
     * @param parkId
     * @throws SQLException 
     */
    public void updateParkName(String parkName, int parkId) throws SQLException {
        String sql = "UPDATE PARQUE SET PARQUE_NOMBRE = ? "
                + " WHERE PARQUE_ID = ?";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, parkName);
        statement.setInt(2, parkId);
        statement.executeUpdate();
    }
    
    /**
     * Actualiza la ID de la cuidad de un parque predeterminado en la BBDD.
     * 
     * @param cityId
     * @param parkId
     * @throws SQLException 
     */
    public void updateParkCityId(int cityId, int parkId) throws SQLException {
        String sql = "UPDATE PARQUE SET CUIDAD_ID = ? "
                + " WHERE PARQUE_ID = ?";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setInt(1, cityId);
        statement.setInt(2, parkId);
        statement.executeUpdate();
    }
    
    /**
     * Actualiza la extensión del parque predeterminado en la BBDD.
     * 
     * @param parkArea
     * @param parkId
     * @throws SQLException 
     */
    public void updateParkArea(double parkArea, int parkId) throws SQLException {
         String sql = "UPDATE PARQUE SET EXTENSION = ? "
                + " WHERE PARQUE_ID = ?";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setDouble(1, parkArea);
        statement.setInt(2, parkId);
        statement.executeUpdate();       
    }
    
    /**
     * Busca la tabla de parques en la BBDD para un nombre de parque que es 
     * igual a la cadena pasado por el parámetro.
     * 
     * @param searchStr
     * @return ArrayList de objetos Park
     * @throws SQLException 
     */
    public ArrayList<Park> searchByStringParks(String searchStr) throws SQLException {
        String sql = "SELECT * FROM PARQUE "
                + "WHERE PARQUE_NOMBRE LIKE ?";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, "%" + searchStr + "%");        
        ResultSet result = statement.executeQuery();
        
        ArrayList<Park> parks = returnParkArrayList(result);
        
        return parks;
    }
    
    /**
     * Borra registros de parques que contiene la ID de cuidad pasado por el
     * parámetro.
     * 
     * @param city
     * @throws SQLException 
     */
    public void deleteParks(City city) throws SQLException {
        String sql = "DELETE FROM PARQUE WHERE CUIDAD_ID = ?";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setInt(1, city.getCityId());
        statement.executeUpdate();
    }
    
    /**
     * Devuele el número de parques que tiene una extensión mejor que la pasado
     * como un parámetro en una cuidad predeterminada identificada por su ID.
     * 
     * @param cityId
     * @param area
     * @return
     * @throws SQLException 
     */
    public int parkArea(int cityId, double area) throws SQLException {
        String sql = "SELECT COUNT(*) FROM PARQUE "
                + "WHERE CUIDAD_ID = ? AND EXTENSION > ?";
          
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setInt(1, cityId);
        statement.setDouble(2, area);
        ResultSet result = statement.executeQuery();
        
        int numParks = 0;
        
        while (result.next()) {
            numParks = result.getInt(1);
        }
        return numParks;
    } 
}
