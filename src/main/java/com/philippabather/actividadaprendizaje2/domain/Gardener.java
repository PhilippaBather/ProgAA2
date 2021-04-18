package com.philippabather.actividadaprendizaje2.domain;

/**
 * Gardener (Jardinero) añade las propiedades especifica al jardinero.
 * 
 * @author philippa bather
 */
public class Gardener extends Employee {    
    //declarar variables de instancia
    private int managerId;
    
    //constructor
    public Gardener(int managerId, int employeeId, String name, String surname, String dni, int teamId) {
        super(employeeId, name, surname, dni, teamId);
        this.managerId = managerId;
    }
    
    //getters y setters

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
    
    //otros métodos

    @Override
    public String toString() {
        return super.toString() + "\nID del Jefe: " + managerId;
    }
    
}
