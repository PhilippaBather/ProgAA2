package com.philippabather.actividadaprendizaje2.domain;

/**
 * City define el objeto cuidad que corresponde a la tabla Cuidad en la BBDD.
 * 
 * @author philippa bather
 */
public class City {
    
    //declarar variables de instancia
    private int cityId;
    private String cityName;
    private String ccaa;
    
    //constructor    
    public City() {
    }

    public City(int cityId, String cityName, String ccaa) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.ccaa = ccaa;
    }
    
    //getters y setters
    
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setRegion(String region) {
        this.ccaa = region;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getRegion() {
        return ccaa;
    }

    //otros métodos
    
    @Override
    public String toString() {
        return "ID: " + cityId + "; Nombre: " + cityName + "; Región: " + ccaa;
    } 
}
