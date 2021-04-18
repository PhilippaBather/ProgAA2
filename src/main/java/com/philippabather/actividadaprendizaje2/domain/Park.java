package com.philippabather.actividadaprendizaje2.domain;

/**
 * Park define el objeto parque que corresponde a la tabla Parque en la BBDD.
 * 
 * @author philippa bather
 */
public class Park {    
    //declarar variables de instancia
    private int parkId;
    private int cityId;
    private String name;
    private double area;

    //constructors    
    public Park() {
    }

    public Park(int cityId, String name, double area) {
        this.cityId = cityId;
        this.name = name;
        this.area = area;
    }
    
    //getters y setters

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getParkId() {
        return parkId;
    }

    public int getCityId() {
        return cityId;
    }
       
    public String getName() {
        return name;
    }

    public double getArea() {
        return area;
    }
   
    @Override
    public String toString() {
        return "Parque nombre: " + name + "\nÉxtension: " + area + "\nCuidad ID: " + cityId;
    }
}
