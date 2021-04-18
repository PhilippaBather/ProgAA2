package com.philippabather.actividadaprendizaje2.dao;

import com.philippabather.actividadaprendizaje2.domain.City;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * CityDAO contiene las consultas de la tabla Cuidad en la BBDD.
 * 
 * @author philippa bather
 */
public class CityDAO {
    //declarar las propiedades de la instancia
    private Conexion con;
    
    //constructor
    public CityDAO(Conexion conexion) {
        this.con = conexion;
    }

    /**
     * Acepta como su parámetro el ResultSet de una consulta y rellena un array
     * list de objeto City con los resultados.
     * 
     * @param result
     * @return ArrayList de objetos City
     * @throws SQLException 
     */
    public ArrayList<City> returnCityArrayList(ResultSet result) throws SQLException {
    
        ArrayList<City> cities = new ArrayList<>();

        while (result.next()) {
            City city = new City();
            city.setCityId(result.getInt(1));
            city.setCityName(result.getString(2));
            city.setRegion(result.getString(3));
            cities.add(city);
        }
        return cities;
    }
          
    /**
     * Devuele los registros de cuidades donde su nombre es igual a la parámetro
     * pasado
     * 
     * @param cityName
     * @return ArrayList de objetos City
     * @throws SQLException 
     */ 
    public ArrayList<City> getCityRecords(String cityName) throws SQLException {
        String sql = "SELECT * FROM CUIDAD WHERE CUIDAD_NOMBRE = ? ";
                   
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, cityName);
        ResultSet result = statement.executeQuery();
        
        ArrayList<City> cities = returnCityArrayList(result);
        
        return cities;
    }
    
    /**
     * Devuele los registros de cuidades en las que existe un parque del nombre
     * pasado como el parámetro.
     * 
     * @param parkName
     * @return ArrayList de objetos City
     * @throws SQLException 
     */
    public ArrayList<City> getCitiesByPark(String parkName) throws SQLException {
        String sql = "SELECT * FROM CUIDAD C "
                    + "INNER JOIN PARQUE P "
                    + "ON C.CUIDAD_ID = P.CUIDAD_ID "
                    + "WHERE P.PARQUE_NOMBRE = ?";

        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, parkName);
        ResultSet result = statement.executeQuery();
            
        ArrayList<City> cities = returnCityArrayList(result);
        
        return cities;
    }
    
    /**
     * Devuele un array list de objetos City en las que hay un parque cuyo nombre
     * es igual de la cadena pasado como parámetro.
     * 
     * @param searchStr
     * @return ArrayList de objetos City
     * @throws SQLException 
     */
    public ArrayList<City> cityStringSearch(String searchStr) throws SQLException {
        String sql = "SELECT * FROM CUIDAD C "
                + "INNER JOIN PARQUE P "
                + "ON C.CUIDAD_ID = P.CUIDAD_ID "
                + "WHERE P.PARQUE_NOMBRE LIKE ?";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, "%" + searchStr + "%");        
        ResultSet result = statement.executeQuery();
        
        ArrayList<City> parks = returnCityArrayList(result);
        
        return parks; 
    }
    
    /**
     * Realiza una inserción de un nuevo registro de una cuidad en la BBDD.
     * 
     * @param newCityName
     * @param newCCAA
     * @throws SQLException 
     */
    public void insertIntoCity(String newCityName, String newCCAA) throws SQLException {
        String sql = "INSERT INTO CUIDAD(CUIDAD_NOMBRE, CCAA) VALUES(?, ?)";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, newCityName);
        statement.setString(2, newCCAA);
        statement.executeUpdate();        
    }
    
    /**
     * Devuele un array list de cuidades que contengan parques cuya suma total
     * de du área sea mayor que la extensión pasado como un parámetro.
     * 
     * @param area
     * @return ArrayList de objetos City
     * @throws SQLException 
     */
    public ArrayList<City> totalArea(double area) throws SQLException {
        String sql = "SELECT C.CUIDAD_ID, C.CUIDAD_NOMBRE, C.CCAA FROM CUIDAD C "
                        + "INNER JOIN PARQUE P ON C.CUIDAD_ID = P.CUIDAD_ID "
                        + "GROUP BY C.CUIDAD_ID, C.CUIDAD_NOMBRE, C.CCAA "
                        + "HAVING SUM(P.EXTENSION) > ?";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setDouble(1, area);
        ResultSet result = statement.executeQuery();        
        
        ArrayList<City> cities = returnCityArrayList(result);
      
        return cities;
    }
    
    public ArrayList<City> getCCAAByName(String ccaa) throws SQLException {
        String sql = "SELECT * FROM CUIDAD WHERE CCAA = ?";
        
        PreparedStatement statement = con.getConexion().prepareStatement(sql);
        statement.setString(1, ccaa);
        ResultSet result = statement.executeQuery();
        
        ArrayList<City> cities = returnCityArrayList(result);
        
        return cities;
    }
}
